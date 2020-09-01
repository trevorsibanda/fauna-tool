package fauna.tool

import fauna.tool.logmonitor.{ LogsMonitor, LogsMonitorConfig }
//import fauna.tool.coverage.CoverageConfig
//import fauna.tool.coverage.Coverage

case class AppConfig(
  val command: String = "help",
  val logs: LogsMonitorConfig = new LogsMonitorConfig(),
  val generator: CodeGenConfig = new CodeGenConfig(),
  val coverage: CoverageConfig = new CoverageConfig()
)

object Application {
  val version = "v0.2.0"
  val author = "Trevor Sibanda <sibandatrevor@gmail.com>"

  val parser = new scopt.OptionParser[AppConfig]("fauna-tool") { self =>
    head("fauna-tool", fauna.tool.Application.version)
    head(s"$author\n")

    cmd("logs")
      .children(LogsMonitor.parserOpts(self): _*)
      .action((_, c) => { c.copy(command = "logs") })

    cmd("code")
      .children(Generator.parserOpts(self): _*)
      .action((_, c) => { c.copy(command = "code") })

    cmd("fuzzer").action((_, c) => { c.copy(command = "fuzzer") })

    cmd("view_error").action((_, c) => { c.copy(command = "view_error") })

    cmd("validate").text("Basic query validator").hidden()

    cmd("coverage")
      .children(Coverage.parserOpts(self): _*)
      .action((_, c) => { c.copy(command = "coverage") })

  }

  def main(args: Array[String]) = {

    parser.parse(args, AppConfig()) match {
      case Some(config) =>
        parser.displayToOut(parser.header)
        config.command match {
          case "logs" => {
            val lm = new LogsMonitor(config.logs)
            lm.run
          }
          case "code"   => Generator.run(config.generator)
          case "server" => ()
          case "view_error" => {
            Generator.viewError(config.generator)
          }
          case "coverage" => {
            val cov = new fauna.tool.coverage.DriverCoverage(config.coverage)
            cov.run
          }
          case _ => parser.displayToOut(parser.usage)
        }

      case None =>
      // arguments are bad, error message will have been displayed
    }
  }

}
