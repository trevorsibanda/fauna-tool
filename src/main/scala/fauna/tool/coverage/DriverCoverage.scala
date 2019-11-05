package fauna.tool.coverage

import scala.collection.mutable
import scala.collection.immutable

import com.typesafe.scalalogging.Logger

import fauna.tool.ast.Expr
import fauna.tool.codegen.Generator
import fauna.tool.logmonitor.{
  FileMonitor,
  LogEntry,
  LogsMonitor,
  LogsMonitorConfig
}

import scala.concurrent.Future

class DriverCoverage(config: CoverageConfig) {

  private val logger = Logger("driver-coverage")

  val followDelayMs: Long = 250

  val file = "/dev/shm/fauna-api-test/api-1/log/query.log"
  val logFile = new java.io.File(file)

  def driverTargets: Seq[String] =
    Seq("unknown") ++ (if (!config.exclude.isEmpty)
                         config.exclude.diff(config.include)
                       else config.include)

  private val hitCounter: immutable.Map[String, mutable.Map[String, Int]] =
    driverTargets.map { s: String =>
      (s, mutable.Map.empty[String, Int])
    }.toMap

  val monitor = new LogsMonitor(logsMonConfig) {

    override def newFileMonitor: FileMonitor =
      new FileMonitor(filters, codegen, processEntry)

    override def shutdownHookHandler = {
      logger.warn("Received signal. Terminating")
      super.shutdownHookHandler
    }

    override def entryHandler(
      entry: LogEntry,
      expr: fauna.tool.ast.Expr,
      codegen: Generator
    ): Unit = Future {
      processEntry(entry, expr, codegen)
    }
  }

  def addHit(driver: String, expr: Expr) = {
    val entry = hitCounter.getOrElse(driver, hitCounter("unknown"))
    val count = entry.getOrElseUpdate(expr.name, -1)
    entry.update(expr.name, count + 1)
  }

  def run = {
    val exprs: Seq[Expr] = Expr.knownExprs
    logger.debug(s"Initializing driver coverage with ${exprs.length} exprs")
    driverTargets.foreach { driver: String =>
      exprs.foreach { expr: Expr =>
        addHit(driver, expr)
      }
    }
    logger.info(config.toString)
    logger.info("Driver code coverage running.")
    if (config.liveTarget)
      logger.info("Ready! Run all driver tests and press [Ctrl + C] when complete.")
    else
      logger.info("Please wait. Tests are running...")
    monitor.run
    showResults()
    logger.info("Coverage test complete")
  }

  def showResults() = {
    println("Coverage results:\n===========================\n")
    hitCounter.keySet.map{driver: String => {
      if(driver != "unknown" && !config.verboseReport){
        println(s" $driver DRIVER:\n----------------\n")
        hitCounter(driver).foreach(tpl => 
          if(tpl._2 <= 0)
            println(s"${tpl._1} has not hits")
        )
      }
    }}
  }

  def logsMonConfig: LogsMonitorConfig =
    new LogsMonitorConfig(logFile = config.logFile, follow = config.liveTarget)

  def processEntry(entry: LogEntry, expr: Expr, codegen: Generator) = {
    val driver = entry.driver.getOrElse("unknown").toLowerCase
    expr.forEachChildren((e: Expr) => {
      addHit(driver, e)
    })
  }

}
