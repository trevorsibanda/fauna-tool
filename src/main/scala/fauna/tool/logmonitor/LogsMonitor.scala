package fauna.tool.logmonitor

import fauna.tool.AppConfig
import fauna.tool.ast.Effect

import scala.io.Source
import org.json4s._
import org.json4s.native.JsonMethods._
import fauna.tool.codegen.Generator

import org.apache.commons.io.input.{ Tailer, TailerListenerAdapter }
import scala.collection.mutable
import scala.concurrent.Future

import com.typesafe.scalalogging.Logger

case class APIRequest(
  method: String,
  path: String,
  query: String,
  headers: Map[String, String],
  body: JValue,
  auth: JValue
)
case class APIResponse(code: Int, errors: Option[List[Map[String, String]]])

case class LogStats(
  query_read_ops: Option[Int],
  query_time: Option[Int],
  query_write_ops: Option[Int],
  query_bytes_in: Option[Int],
  query_bytes_out: Option[Int],
  storage_bytes_write: Option[Int],
  storage_bytes_read: Option[Int],
  txn_retries: Option[Int]
)

case class LogEntry(
  request: APIRequest,
  response: APIResponse,
  stats: LogStats,
  ip: Option[String],
  host: Option[String],
  ts: Option[String],
  driver: Option[String]
)

class FileMonitor(
  filters: List[Filter],
  codegen: Generator,
  handler: (LogEntry, fauna.tool.ast.Expr, Generator) => Unit
)(
  implicit bf: fauna.tool.parser.ASTBuilder[org.json4s.JValue]
) extends TailerListenerAdapter {

  override def handle(line: String) =
    LogsMonitor.handle(line, filters, codegen, handler)

}

class LogsMonitor(config: LogsMonitorConfig) {

  val logger = Logger("logs-monitor")

  val codegen: Generator = new fauna.tool.codegen.JSCodeGenerator()

  implicit val bf = new fauna.tool.parser.JsonASTBuilder()
  fauna.tool.ast.Expr.reg(bf)

  val filters: List[Filter] = loadFilters(config)

  private[tool] def newFileMonitor =
    new FileMonitor(filters, codegen, LogsMonitor.showEntry)

  def shutdownHookHandler: Unit = {
    logger.info("Shutting down log monitor")
  }

  def run = {
    val logFile: java.io.File = new java.io.File(config.logFile match {
      case "" =>
        logger.warn("No log path specified. Attempting to detect")
        LogsMonitor
          .detectLogPath(logger)
          .getOrElse(throw new Exception("No Log file found"))
      case _ => config.logFile
    })

    val mainThread = Thread.currentThread();

    if (config.follow) {
      val follower = newFileMonitor
      val tailer = new Tailer(logFile, follower, config.followDelayMs, config.follow)

      Runtime.getRuntime.addShutdownHook(new Thread() {
        override def run = {
          shutdownHookHandler
          tailer.stop()
          mainThread.join()

        }
      })

      tailer.run()
    } else {
      val source = Source.fromFile(logFile)

      Runtime.getRuntime.addShutdownHook(new Thread() {
        override def run = {
          source.close()
          mainThread.join()
        }
      })

      source.getLines.foreach {
        case line => LogsMonitor.handle(line, filters, codegen, entryHandler)(bf)
      }
      source.close()
    }

  }

  implicit val ec: scala.concurrent.ExecutionContext =
    scala.concurrent.ExecutionContext.global

  def entryHandler(
    entry: LogEntry,
    expr: fauna.tool.ast.Expr,
    codegen: Generator
  ): Unit = {
    LogsMonitor.showEntry(entry, expr, codegen)
  }

  def loadFilters(cfg: LogsMonitorConfig): List[Filter] = {
    val lb = new mutable.ListBuffer[Filter]()

    if (!cfg.filterDrivers.isEmpty) {
      lb.addOne(QueryDriverFilter(cfg.filterDrivers))
    }

    if (!cfg.filterQueryEffect.isEmpty())
      lb.addOne(QueryEffectFilter(cfg.filterQueryEffect match {
        case "pure" | "PURE" | "p"   => Effect.Pure
        case "write" | "Write" | "w" => Effect.Write
        case "read" | "READ" | "r"   => Effect.Read
      }))

    if (cfg.filterErrorResponse)
      lb.addOne(HttpErrorResponseFilter(true))

    if (cfg.filterResponseCode != 0)
      lb.addOne(HttpResponseFilter(cfg.filterResponseCode))

    if (!cfg.filterRegexQuery.isEmpty)
      lb.addOne(QueryRegexFilter(this.codegen, cfg.filterRegexQuery))

    if (!cfg.filterTimestampStart.isEmpty)
      lb.addOne(
        TimeStampFilter(
          cfg.filterTimestampStart,
          if (cfg.filterTimestampEnd.isEmpty) None
          else Some(cfg.filterTimestampEnd)
        )
      )

    if (!cfg.filterQueryContainsFns.isEmpty)
      lb.addOne(QueryFilter(cfg.filterQueryContainsFns))

    if (!cfg.filterQueryContainsExpr.isEmpty) {
      val text = cfg.filterQueryContainsExpr
      val fqlBuilder = new FQLBuilder()
      fauna.tool.ast.Expr.reg(fqlBuilder)
      Seq(Try {
        bf.build(parse(text))
      }, Try {
        fqlBuilder.build(text)
      }).collectFirst {
          case Success(expr) => lb.addOne(QueryContainsExprFilter(expr, false, Nil))
        }
        .fold({
          logger.warn("Failed to parse argument for --fqe")
        })(_ => ())
    }

    lb.toList
  }

}

object LogsMonitor {

  implicit val jsonDefaultFormats = DefaultFormats

  def handle(
    line: String,
    filters: List[Filter],
    codegen: Generator,
    process: (LogEntry, fauna.tool.ast.Expr, Generator) => Unit
  )(
    implicit bf: fauna.tool.parser.ASTBuilder[org.json4s.JValue]
  ) = {
    val entry = parse(line).extract[LogEntry]
    val expr = fauna.tool.ast.Expr.build(entry.request.body)
    if (filter(entry, Some(expr), filters)) {
      process(entry, expr, codegen)
    }
  }

  def detectLogPath(logger: Logger): Option[String] = {
    Seq(
      "/var/log/faunadb/",
      "/usr/share/faunadb/log/",
      "/var/db/fauna/log/",
      "/dev/shm/fauna-api-test/api-1/log/",
      "/var/run/faunadb/log/"
    ).filter((dir: String) => {
        val path = java.nio.file.Paths.get((s"$dir/query.log"))
        java.nio.file.Files.exists(path) && java.nio.file.Files.isReadable(path)
      })
      .headOption match {
      case None => {
        logger.warn("Failed to find faunadb log path")
        None
      }
      case Some(p) => {
        logger.info(s"Found query.log at $p")
        Some(s"$p/query.log")
      }
    }
  }

  def filter(
    entry: LogEntry,
    expr: Option[fauna.tool.ast.Expr],
    filters: List[Filter]
  ): Boolean = {
    val defv: Boolean = true
    filters
      .fold(defv) {
        case (accum: Boolean, filter: Filter) => {
          accum & filter.apply(entry, expr)
        }
      }
      .asInstanceOf[Boolean]
  }

  def showEntry(entry: LogEntry, expr: fauna.tool.ast.Expr, codegen: Generator) = {
    val query = codegen.exprToCode(expr)

    val driverName =
      entry.driver
        .getOrElse(entry.request.headers.getOrElse("user_agent", "N/A"))
        .toUpperCase
    val overlayColor = expr.effect match {
      case Effect.Pure  => fansi.Color.LightGreen
      case Effect.Read  => fansi.Color.Cyan
      case Effect.Write => fansi.Color.Red
    }
    val req = fansi
      .Str(
        s"[ ${expr.effect.toString.padTo(5, ' ')} ]  HTTP/${entry.response.code} $driverName"
      )
      .overlay(overlayColor, 0, 9)
      .overlay(fansi.Reversed.On, 0, 9)
    val qry = s"[ query ] $query"
    val raw =
      if (entry.request.body != JNothing)
        s"[  raw  ] ${compact(render(entry.request.body))}"
      else ""
    s"[  auth  ]   $qry"
    s"[  stats ]    "
    val msg = s"$req\n$raw\n$qry\n\n"
    println(msg)
  }

  def parserOpts(
    p: scopt.OptionParser[AppConfig]
  ): Seq[scopt.OptionDef[_, AppConfig]] = Seq(
    p.opt[String]('q', "qlog")
      .valueName("<file>")
      .action((x, c) => c.copy(logs = c.logs.copy(logFile = x)))
      .text("Path to query.log file"),
    p.opt[Unit]('f', "follow")
      .valueName("<file>")
      .action((x, c) => c.copy(logs = c.logs.copy(follow = true)))
      .text("Monitor file and process new queries"),
    p.opt[Int]("delay")
      .valueName("<milliseconds>")
      .text("Delay time in ms. Use with --follow")
      .action((x, c) => c.copy(logs = c.logs.copy(followDelayMs = x))),
    p.opt[String]("codegenerator")
      .abbr("cg")
      .valueName("<code generator>")
      .action((x, c) => c.copy(logs = c.logs.copy(codegen = x)))
      .text("Available code generators: js,curl"),
    p.opt[String]("filter-drivers")
      .abbr("fd")
      .valueName("<drivers>")
      .action(
        (x, c) => c.copy(logs = c.logs.copy(filterDrivers = x.split(",")))
      )
      .text("Comma seperated list of driver requests to show"),
    p.opt[Boolean]("filter-bad-requests")
      .abbr("fb")
      .text("Filter out queries generation 50x/40x HTTP responses")
      .action((x, c) => c.copy(logs = c.logs.copy(filterErrorResponse = x))),
    p.opt[String]("filter-query")
      .abbr("fq")
      .valueName("<list of fql functions>")
      .text("Comma seperated list of FQL functions")
      .action(
        (x, c) => c.copy(logs = c.logs.copy(filterQueryContainsFns = x.split(",")))
      ),
    p.opt[String]("filter-query-regexp")
      .abbr("fr")
      .valueName("<regexp>")
      .text(
        "Regex expression to match againt the query generated by the code generator"
      )
      .action((x, c) => c.copy(logs = c.logs.copy(filterRegexQuery = x))),
    p.opt[String]("filter-query-effect")
      .abbr("fe")
      .valueName("<effect>")
      .text("Filter query by effect. Options: pure, read, write")
      .action((x, c) => c.copy(logs = c.logs.copy(filterQueryEffect = x))),
    p.opt[String]("filter-timestamp-start")
      .valueName("<timestamp>")
      .text("Filter requests starting from timestamp")
      .action((x, c) => c.copy(logs = c.logs.copy(filterTimestampStart = x))),
    p.opt[String]("filter-timestamp-end")
      .valueName("<timestamp>")
      .text("Filter requests between start and end timestamp")
      .action((x, c) => c.copy(logs = c.logs.copy(filterTimestampStart = x))),
    p.opt[Int]("filter-response-code")
      .valueName("<http_response_code>")
      .text("HTTP response code")
      .action((x, c) => c.copy(logs = c.logs.copy(filterResponseCode = x)))
  )

}
