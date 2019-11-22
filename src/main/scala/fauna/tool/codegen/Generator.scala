package fauna.tool.codegen

import fauna.tool.AppConfig

import fauna.tool.ast.{ Arity, Expr, FaunaValue, Literal }
import fauna.tool.ast.{
  ArrayL,
  DecimalL,
  DoubleL,
  FalseL,
  FloatL,
  IntL,
  LongL,
  NullL,
  ObjectL,
  StringL,
  TrueL
}
import fauna.tool.ast.{ BytesV, DateV, QueryV, SetV, TimestampV }
import fauna.tool.ast.{
  Collections,
  Databases,
  Functions,
  Indexes,
  Keys,
  Logout,
  Tokens
}
import fauna.tool.parser.ASTBuilder
import fauna.tool.ast.UnknownExpression

import com.typesafe.scalalogging.Logger
import scala.util.Try

import fauna.tool.parser.RandomASTBuilder

case class GeneratorException(msg: String) extends Exception(msg)

case class CodeGenConfig(
  input: String = "",
  eval: String = "",
  format: String = "fql",
  codegen: String = "js",
  output: String = ""
)

trait Generator {
  type Code = String
  type CodeBuilder = StringBuilder

  val logger: Logger = Logger(this.getClass())

  val name: String

  val allowUnknownExpr: Boolean

  implicit val builder: ASTBuilder[_]

  def blockComment(code: Code): Code = s"\\*$code*\\"

  def literalToCode(l: Literal): Code = l match {
    case LongL(l)    => s"${l.toLong}"
    case DoubleL(d)  => s"${d.toDouble}"
    case FloatL(f)   => s"${f.toFloat}"
    case DecimalL(d) => s"${d}"
    case IntL(i)     => s"$i"
    case ArrayL(l)   => l.map(exprToCode).mkString("[", ", ", "]")
    case ObjectL(m) =>
      m.map {
          case (k, v) => s""""$k": ${exprToCode(v)}"""
        }
        .mkString("{", ",", "}")
    case StringL(s) => s""""${s.replaceAll("\"", "\\\"")}""""
    case TrueL      => "true"
    case FalseL     => "false"
    case NullL      => "null"
  }

  def faunaValueToCode(value: FaunaValue): Code = value match {
    case SetV(s)        => s"""Set(${exprToCode(s)})"""
    case DateV(v)       => s"""Date(${exprToCode(v)})"""
    case BytesV(b)      => s"""Bytes(${exprToCode(b)})"""
    case TimestampV(ts) => s"""TimeStamp(${exprToCode(ts)})"""
    case QueryV(q)      => s"""Query(${exprToCode(q)})"""
  }

  def exprToCode(expr: Expr): Code = expr match {
    case UnknownExpression(jvalue) => {
      if (!allowUnknownExpr) {
        throw new GeneratorException(
          "Failed to generate code. Found UnknownExpression"
        )
      } else {
        blockComment(s"UnknownExpression($jvalue)")
      }
    }
    case e @ (Databases(NullL) | Collections(NullL) | Indexes(NullL) | Keys(NullL) |
        Tokens(NullL) | Functions(NullL) | Logout(NullL)) =>
      s"${e.name}()"
    case fv: FaunaValue => faunaValueToCode(fv)
    case l: Literal     => literalToCode(l)
    case e: Expr if e.arity.isInstanceOf[Arity.Exact] =>
      e.children
        .map {
          case Some(x) => exprToCode(x)
        }
        .mkString(s"${e.name}(", ",", ")")

    case e: Expr if e.arity.isInstanceOf[Arity.Between] =>
      e.children
        .collect {
          case Some(x) => exprToCode(x)
        }
        .mkString(s"${e.name}(", ",", ")")
    case e: Expr if e.arity == Arity.VarArgs =>
      e.children
        .map {
          case Some(x) => exprToCode(x)
        }
        .mkString(s"${e.name}(", ",", ")")

  }

  def code: Code

}

object Generator {

  def run(config: CodeGenConfig) = {
    import org.json4s._
    import org.json4s.native.JsonMethods._

    val logger = Logger("codegen-run")

    implicit val bf = config.format match {
      case "fql"  => new fauna.tool.parser.FQLBuilder()
      case "json" => new fauna.tool.parser.JsonASTBuilder()
      case "rand" => new fauna.tool.parser.RandomASTBuilder(0)
    }
    Expr.reg(bf)

    val cgen = config.codegen match {
      case "curl" => new CurlGenerator()
      case "js"   => new JSCodeGenerator()
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
        case "fql"  => Expr.build(line)(bf.asInstanceOf[ASTBuilder[String]])
        case "json" => Expr.build(parse(line))(bf.asInstanceOf[ASTBuilder[JValue]])
        case "rand" => {
          val e: Expr = ObjectExpr(NullL);
          val r = Expr.build(e)(bf.asInstanceOf[ASTBuilder[Expr]]);
          r match {
            case ObjectExpr(obj) => obj
            case _               => r
          }
        }
      }

      println(
        s"AST:\t\t${ast}\nCODE:\t\t${cgen
          .exprToCode(ast)}\nJSON:\t\t${compact(render(ast.toJson))}\nVAL:\t\t${ast.validate}\n${ast.evaluatesTo} \n"
      )

    }

  }

  def parserOpts(
    p: scopt.OptionParser[AppConfig]
  ): Seq[scopt.OptionDef[_, AppConfig]] = Seq(
    p.opt[String]("eval")
      .valueName("<query>")
      .action((x, c) => c.copy(generator = c.generator.copy(eval = x)))
      .text("Query in form of raw json or fql"),
    p.opt[String]('i', "input")
      .valueName("<file|query>")
      .action((x, c) => c.copy(generator = c.generator.copy(input = x)))
      .text("Path to input file. Use -- for stdin")
      .withFallback(() => "--"),
    p.opt[String]("codegenerator")
      .abbr("cg")
      .valueName("<code generator>")
      .action((x, c) => c.copy(generator = c.generator.copy(codegen = x)))
      .text("Available code generators: js,curl")
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
