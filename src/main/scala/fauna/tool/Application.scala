package fauna.tool

import scala.io.Source
import fauna.tool.ast.{ Expr }
import java.security.Timestamp

import scopt.OParser

import fauna.tool.logmonitor.{ HttpErrorResponseFilter, TimeStampFilter }
import fauna.tool.logmonitor.{ LogsMonitor, LogsMonitorConfig }
import fauna.tool.codegen.{ CodeGenConfig, Generator }
import fauna.tool.coverage.CoverageConfig

import fauna.tool.codegen.{ CurlGenerator, JSCodeGenerator }
import fauna.tool.coverage.Coverage

case class AppConfig(
  val command: String = "help",
  val logs: LogsMonitorConfig = new LogsMonitorConfig(),
  val generator: CodeGenConfig = new CodeGenConfig(),
  val coverage: CoverageConfig = new CoverageConfig()
)

object Application {
  val version = "v0.1.0"
  val author = "Trevor Sibanda <sibandatrevor@gmail.com>"

  val parser = new scopt.OptionParser[AppConfig]("fauna-tool") { self =>
    head("fauna-tool", fauna.tool.Application.version)
    head(s"$author\n")

    cmd("logs")
      .children(LogsMonitor.parserOpts(self): _*)
      .action((x, c) => { c.copy(command = "logs") })

    cmd("code")
      .children(Generator.parserOpts(self): _*)
      .action((x, c) => { c.copy(command = "code") })

    cmd("fuzzer").hidden()

    cmd("validate").text("Basic query validator").hidden()

    cmd("coverage")
      .children(Coverage.parserOpts(self): _*)
      .action((x, c) => { c.copy(command = "coverage") })
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
