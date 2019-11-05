package fauna.tool.coverage

import fauna.tool.AppConfig

object Coverage {

  def parserOpts(
    p: scopt.OptionParser[AppConfig]
  ): Seq[scopt.OptionDef[_, AppConfig]] = Seq(
    p.opt[String]('q', "qlog")
      .valueName("<file>")
      .action((x, c) => c.copy(coverage = c.coverage.copy(logFile = x)))
      .text("Path to query.log file"),
    p.opt[Unit]("live")
      .action((x, c) => c.copy(coverage = c.coverage.copy(liveTarget = true)))
      .text(
        "Instrument from live logs. If specified --qlog should point to a query.log file instead of logs directory"
      ),
    p.opt[Unit]("progress")
      .text("Show instrumentation progress")
      .action((x, c) => c.copy(coverage = c.coverage.copy(showProgress = true))),
    p.opt[Seq[String]]("include")
      .valueName("<list of drivers>")
      .action((x, c) => c.copy(coverage = c.coverage.copy(include = x)))
      .text("Specify drivers to include in test"),
    p.opt[Seq[String]]("exclude")
      .valueName("<list of drivers>")
      .action(
        (x, c) => c.copy(coverage = c.coverage.copy(exclude = x))
      )
      .text("Comma seperated list of driver requests to exclude from test"),
    p.opt[String]("html-report")
      .valueName("<path to output html file>")
      .text("Path to html output file")
      .action(
        (x, c) => c.copy(coverage = c.coverage.copy(htmlReportFile = x))
      ),
    p.opt[Unit]('v', "verbose")
      .action((x, c) => c.copy(coverage = c.coverage.copy(verboseReport = true)))
  )
}
