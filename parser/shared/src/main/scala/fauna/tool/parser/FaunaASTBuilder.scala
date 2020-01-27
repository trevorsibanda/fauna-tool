package fauna.tool.parser

import fauna.tool.ast.{
  Arity,
  ArrayL,
  BooleanL,
  Builder,
  DecimalL,
  Expr,
  IntL,
  Literal,
  NullL,
  NumericL,
  ObjectExpr,
  ObjectL,
  StringL,
  Undefined
}
import fastparse._

import fauna.tool.parser.FQL.MethodCall
import fauna.tool.ast.Form

class FQLBuilder extends Builder[String] {

  implicit val bf: fauna.tool.ast.Builder[Expr] = new FQL_Builder()

  override def buildChild(parent: String, childName: String): Expr = ???

  override def buildChildOpt(
    parent: String,
    childName: String
  ): Option[fauna.tool.ast.Expr] = ???

  def buildChild(parent: Expr, childName: String): Expr =
    bf.buildChild(parent, childName)

  override def extractLiteral(value: String): Literal = ???

  override def matchKeys(
    value: String,
    keys: Set[String]
  ): Option[fauna.tool.ast.Form.FormBuilder[Any]] = ???

  override def buildOpt(value: String): Option[Expr] = ???

  override def build(value: String): Expr = bf.build(parseT(value))

  override def readChild(value: String, childName: String): String = ???

  def parseT(s: String): Expr = fastparse.parse(s, FQL.EXPRESSION(_)).get.value
}

private[parser] class FQL_Builder extends Builder[Expr] {

  def readChild(parent: Expr, childName: String): Expr =
    readArgument(parent.asInstanceOf[MethodCall], childName).getOrElse(Undefined)

  override def matchKeys(
    value: Expr,
    keys: Set[String]
  ): Option[fauna.tool.ast.Form.FormBuilder[Any]] = ???

  override def build(e: Expr): Expr = e match {
    case q: FQL.MethodCall => {
      Form.matchesArguments(q.function.s, q.arguments) match {
        case Some((builder, args)) =>
          builder(q, this.asInstanceOf[fauna.tool.ast.Builder[Any]])
        case None => Undefined
      }
    }
    case l: Literal => extractLiteral(l)
    case v: Expr    => v
  }

  def buildChild(parent: Expr, childName: String): Expr =
    buildChildOpt(parent, childName) match {
      case Some(e) => e
      case None    => Undefined
    }

  def buildChildOpt(parent: Expr, childName: String): Option[Expr] = parent match {
    case q: FQL.MethodCall =>
      readArgument(q, childName) match {
        case Some(mc) =>
          mc match {
            case c: MethodCall => Some(this.build(c))
            case _             => Some(build(mc))
          }
        case None => None
      }
    case e: Expr => Some(e)
  }

  override def buildOpt(value: Expr): Option[Expr] = ???

  override def extractLiteral(value: Expr): Literal = value match {
    case ArrayL(l)  => ArrayL(l.map(build))
    case ObjectL(m) => ObjectL(m.map(tpl => (tpl._1, build(tpl._2))))
    case l: Literal => l
  }

  def readArgument(q: FQL.MethodCall, param: String): Option[Expr] =
    Form.matchesArguments(q.function.s, q.arguments) match {
      case Some((_, args)) =>
        args.collectFirst {
          case arg if arg._1 == param => arg._2
        }
      case None => None
    }

}

private[parser] object FQL {
  abstract class Argument(val value: Expr) {}

  case class IndexedArgument(i: Int, override val value: Expr)
      extends Argument(value)

  case class NamedArgument(param: String, override val value: Expr)
      extends Argument(value)

  case class MethodCall(function: StringL, arguments: Seq[Expr]) extends Expr {
    override def name: String = function.s

    override def children: Seq[Option[Expr]] = arguments.map(Some(_))

  }

  implicit def whitespace(cfg: P[_]): P[Unit] = Lexical.wscomment(cfg)

  def NUMBER[_: P]: P[NumericL[_]] =
    P(Lexical.floatnumber | Lexical.longinteger | Lexical.integer).map {
      case bd: BigDecimal => DecimalL(bd)
      case bi: BigInt     => IntL(bi)
    }

  def BOOLEAN[_: P]: P[BooleanL] =
    P(Lexical.kw("true") | Lexical.kw("false")).!.map { b: String =>
      BooleanL(b == "true")
    }

  def NULL[_: P]: P[Expr] = P(Lexical.kw("null")).map(_ => NullL)

  def STRING[_: P]: P[StringL] = Lexical.stringliteral.map(StringL)

  def LIST[_: P]: P[ArrayL] =
    P("[" ~ (EXPRESSION).rep(0, sep = ",") ~ "]").map(seq => ArrayL(seq.toList))

  def EXPRESSION[_: P]: P[Expr] =
    P(NUMBER | STRING | BOOLEAN | LIST | NULL | OBJECT | BINDING | CALL)

  def kvPair[_: P]: P[(String, Expr)] =
    P(P(Lexical.identifier | Lexical.stringliteral) ~ ":" ~ EXPRESSION)

  def OBJECT[_: P]: P[ObjectExpr] = P("{" ~ kvPair.rep(0, sep = ",") ~ "}").map {
    seq =>
      ObjectExpr(ObjectL(seq.toMap))
  }

  def BINDING[_: P]: P[ObjectL] = P(Lexical.kw("binding") ~ OBJECT).map {
    case ObjectExpr(ObjectL(m)) => ObjectL(m)
  }

  def CALL[_: P]: P[MethodCall] =
    P(Lexical.identifier ~ "(" ~ (EXPRESSION.rep(0, sep = ",")).? ~ ")")
      .map(tpl => {
        val args = tpl._2.getOrElse(Nil)
        MethodCall(StringL(tpl._1), if (args.isEmpty) Seq(NullL) else args)
      })

  def EXPRESSIONS[_: P]: P[Seq[Expr]] = P(EXPRESSION.rep(0, sep = "\n"))

}

private[parser] object Lexical {

  implicit def whitespace(cfg: P[_]): P[Unit] = Lexical.wscomment(cfg)

  def lowercase[_: P] = P(CharIn("a-z"))
  def uppercase[_: P] = P(CharIn("A-Z"))
  def digit[_: P] = P(CharIn("0-9"))
  def letter[_: P] = P(lowercase | uppercase)

  def identifier[_: P]: P[String] =
    P((letter | "_") ~ (letter | digit | "_").rep).!

  def kw[_: P](s: String) = s ~ !(letter | digit | "_")
  def comment[_: P] = P("//" ~ CharsWhile(_ != '\n', 0))
  def wscomment[_: P] = P((CharsWhileIn(" \n\t") | comment | "\\\n").rep)
  def nonewlinewscomment[_: P] = P((CharsWhileIn(" ") | comment | "\\\n").rep)

  def stringliteral[_: P]: P[String] = P(shortstring)

  def shortstring[_: P]: P[String] = P(shortstring0("'") | shortstring0("\""))

  def shortstring0[_: P](delimiter: String) =
    P(delimiter ~ shortstringitem(delimiter).rep.! ~ delimiter)

  def shortstringitem[_: P](quote: String): P[Unit] =
    P(shortstringchar(quote) | escapeseq)

  def shortstringchar[_: P](quote: String): P[Unit] =
    P(CharsWhile(!s"\\\n${quote(0)}".contains(_)))

  def escapeseq[_: P]: P[Unit] = P("\\" ~ AnyChar)

  def negatable[T, _: P](p: => P[T])(implicit ev: Numeric[T]) =
    (("+" | "-").?.! ~ p).map {
      case ("-", i) => ev.negate(i)
      case (_, i)   => i
    }

  def longinteger[_: P]: P[BigInt] = P(integer ~ ("l" | "L"))

  def integer[_: P]: P[BigInt] =
    negatable[BigInt, Any](P(octinteger | hexinteger | bininteger | decimalinteger))

  def decimalinteger[_: P]: P[BigInt] =
    P(nonzerodigit ~ digit.rep | "0").!.map(scala.BigInt(_))

  def octinteger[_: P]: P[BigInt] =
    P("0" ~ ("o" | "O") ~ octdigit.rep(1).! | "0" ~ octdigit.rep(1).!)
      .map(scala.BigInt(_, 8))

  def hexinteger[_: P]: P[BigInt] =
    P("0" ~ ("x" | "X") ~ hexdigit.rep(1).!).map(scala.BigInt(_, 16))

  def bininteger[_: P]: P[BigInt] =
    P("0" ~ ("b" | "B") ~ bindigit.rep(1).!).map(scala.BigInt(_, 2))
  def nonzerodigit[_: P]: P[Unit] = P(CharIn("1-9"))
  def octdigit[_: P]: P[Unit] = P(CharIn("0-7"))
  def bindigit[_: P]: P[Unit] = P("0" | "1")
  def hexdigit[_: P]: P[Unit] = P(digit | CharIn("a-f", "A-F"))

  def floatnumber[_: P]: P[BigDecimal] =
    negatable[BigDecimal, Any](P(pointfloat | exponentfloat))

  def pointfloat[_: P]: P[BigDecimal] =
    P(intpart.? ~ fraction | intpart ~ ".").!.map(BigDecimal(_))

  def exponentfloat[_: P]: P[BigDecimal] =
    P((intpart | pointfloat) ~ exponent).!.map(BigDecimal(_))
  def intpart[_: P]: P[BigDecimal] = P(digit.rep(1)).!.map(BigDecimal(_))
  def fraction[_: P]: P[Unit] = P("." ~ digit.rep(1))
  def exponent[_: P]: P[Unit] = P(("e" | "E") ~ ("+" | "-").? ~ digit.rep(1))

}
