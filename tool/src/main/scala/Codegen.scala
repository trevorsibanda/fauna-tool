package fauna.tool

import scala.util.{ Try }

import fauna.tool.ast.{ Builder, Expr }
import fauna.tool.ast.{ Add, NullL }
import fauna.tool.codegen._
import com.typesafe.scalalogging.Logger

case class CodeGenConfig(
  input: String = "--",
  eval: String = "",
  format: String = "fql",
  codegen: String = "js",
  output: String = ""
)

object Generator {

  val jsonEncoder = new fauna.tool.ast.JsonSerializer

  def viewError(config: CodeGenConfig) = {
    import org.json4s._
    import org.json4s.native.JsonMethods._

    val logger = Logger("codegen-run")

    implicit val bf = "json" match {
      case "fql"  => new fauna.tool.parser.FQLBuilder()
      case "json" => new fauna.tool.parser.JsonBuilder()
      //case "rand" => new fauna.tool.parser.RandomBuilder(0)
    }
    Expr.init

    val cgen = config.codegen match {
      case "curl"   => new CurlGenerator(new fauna.tool.ast.JsonSerializer())
      case "js"     => new JSCodeGenerator()
      case "go"     => new GolangCodeGenerator()
      case "python" => new PythonCodeGenerator()
    }

    "eval" match {
      case "--" => {
        logger.info(s"Reading ${config.format} from stdin")
        runner(scala.io.StdIn.readLine())
      }
      case "eval" => {
        runner(
          """{"let":{"id":"1234","collection":"hey"},"in":{"to_string":{"var":"x"}}}"""
        )
      }
      case _ =>
        Try {
          scala.io.Source.fromFile(config.input).getLines().foreach(runner)
        }.getOrElse(runner(config.input))
    }

    def runner(line: String): Unit = {
      val ast = "json" match {
        case "fql" => Expr.build(line)(bf.asInstanceOf[Builder[String]])
        case "json" =>
          Expr.build(traverseJson(parse(line), "in/to_string/var".split("/").toSeq))(
            bf.asInstanceOf[Builder[JValue]]
          )
        case "rand" => {
          val e: Expr = Add(NullL);
          val r = Expr.build(e)(bf.asInstanceOf[Builder[Expr]]);
          r match {
            case Add(obj) => obj
            case _        => r
          }
        }
      }
      println(
        s"AST:\t\t${ast}\nCODE:\t\t${cgen.exprToCode(ast)}\nJSON:\t\t${jsonEncoder.encodeString(ast)}"
      )
    }

    def traverseJson(json: JValue, pos: Seq[String]): JValue =
      pos.foldLeft(json)((j: JValue, key: String) => j \ key)

  }

  def run(config: CodeGenConfig) = {
    import org.json4s._
    import org.json4s.native.JsonMethods._

    val logger = Logger("codegen-run")

    implicit val bf = config.format match {
      case "fql"  => new fauna.tool.parser.FQLBuilder()
      case "json" => new fauna.tool.parser.JsonBuilder()
      //case "rand" => new fauna.tool.parser.RandomBuilder(0)
    }
    Expr.init

    val cgen = config.codegen match {
      case "curl"   => new CurlGenerator(new fauna.tool.ast.JsonSerializer())
      case "js"     => new JSCodeGenerator()
      case "go"     => new GolangCodeGenerator()
      case "python" => new PythonCodeGenerator()
    }

    config.input match {
      case "--" => {
        logger.info(s"Reading ${config.format} from stdin")
        runner(scala.io.StdIn.readLine())
      }
      case "eval" => {
        runner(config.eval)
      }
      case _ =>
        Try {
          scala.io.Source.fromFile(config.input).getLines().foreach(runner)
        }.getOrElse(runner(config.input))
    }

    def runner(line: String): Unit = {
      val ast = config.format match {
        case "fql"  => Expr.build(line)(bf.asInstanceOf[Builder[String]])
        case "json" => Expr.build(parse(line))(bf.asInstanceOf[Builder[JValue]])
        case "rand" => {
          val e: Expr = Add(NullL);
          val r = Expr.build(e)(bf.asInstanceOf[Builder[Expr]]);
          r match {
            case Add(obj) => obj
            case _        => r
          }
        }
      }

      println(
        s"AST:\t\t${ast}\nCODE:\t\t${cgen.exprToCode(ast)}\nJSON:\t\t${jsonEncoder.encodeString(ast)}"
      )

    }

  }

  def parserOpts(
    p: scopt.OptionParser[AppConfig]
  ): Seq[scopt.OptionDef[_, AppConfig]] = Seq(
    p.opt[String]("eval")
      .valueName("<query>")
      .action(
        (x, c) => c.copy(generator = c.generator.copy(eval = x, input = "eval"))
      )
      .text("Query in form of raw json or fql"),
    p.opt[String]('i', "input")
      .valueName("<file|query>")
      .action((x, c) => c.copy(generator = c.generator.copy(input = x)))
      .text("Path to input file. Use -- for stdin"),
    p.opt[String]("codegenerator")
      .abbr("cg")
      .valueName("<code generator>")
      .action((x, c) => c.copy(generator = c.generator.copy(codegen = x)))
      .text("Available code generators: js,curl,go,python")
      .withFallback(() => "js"),
    p.opt[String]('f', "format")
      .valueName("<format>")
      .action((x, c) => c.copy(generator = c.generator.copy(format = x)))
      .text("Input format source. [fql, json]")
      .withFallback(() => "fql"),
    p.opt[String]('o', "output")
      .hidden()
      .valueName("<file>")
      .action((x, c) => c.copy(generator = c.generator.copy(output = x)))
      .text("Path to ouput file. Use -- for stdout")
      .withFallback(() => "--")
  )

}
