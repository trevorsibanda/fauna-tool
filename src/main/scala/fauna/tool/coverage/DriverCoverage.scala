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

  val driverTargets: Seq[String] =
    (if (config.exclude.isEmpty)
       config.include
     else config.include.diff(config.exclude)) :+ "other"

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
    val entry = hitCounter.getOrElse(driver, hitCounter("other"))
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
    driverTargets.map { driver: String =>
      val uniqueHits: Double = hitCounter(driver).values.filter(_ > 0).size
      val totalExprs: Double = hitCounter(driver).size
      val percent = 100.0 * (uniqueHits / totalExprs)
      val pbar = (0 to percent.toInt)
        .map(i => fansi.Back.True(0, (127 + (i * 1.28)).toInt, 0)(" "))
        .mkString
      val npbar = (0 until 100 - percent.toInt)
        .map(i => fansi.Back.True(255, 0, 0)(" "))
        .mkString
      println(
        s"${driver.toUpperCase.padTo(10, ' ')}  ${pbar}${npbar}  ${percent.ceil}% cov \n"
      )
      if (config.verboseReport || driverTargets.length == 1)
        detailedReport(driver, hitCounter(driver))
      println("\n\n")
    }
  }

  def detailedReport(driver: String, counter: mutable.Map[String, Int]): Unit = {
    val noHits = counter.collect {
      case (fn: String, count: Int) if count <= 0 => fn
    }
    val totalHits = counter.values.sum
    val maxHits = counter.values.max
    val mostFrequent = counter
      .collect {
        case (fn: String, count: Int) if count >= maxHits => fn
      }
      .mkString(" ,")
    println(s"Statements Invoked:\t\t$totalHits")
    println(s"Most Frequent Expr:\t\t$mostFrequent")
    println(s"Not Invoked       :\t\t${noHits.mkString(", ")}")
  }

  def logsMonConfig: LogsMonitorConfig =
    new LogsMonitorConfig(logFile = config.logFile, follow = config.liveTarget)

  def processEntry(entry: LogEntry, expr: Expr, codegen: Generator) = {
    val driver = entry.driver.getOrElse("other").toLowerCase
    expr.forEachChildren((e: Expr) => {
      addHit(driver, e)
    })
  }

}
