package fauna.tool.parser

import fauna.tool.ast.{
  Arity,
  ArrayL,
  BooleanL,
  DecimalL,
  Expr,
  IntL,
  Literal,
  NullL,
  NumericL,
  ObjectL,
  StringL,
  Undefined
}
import fastparse._

import fauna.tool.parser.FQL.MethodCall

class FQLBuilder extends ASTBuilder[String] {

  implicit val bf: ASTBuilder[Expr] = new FQL_ASTBuilder()

  Expr.reg(bf)

  override def buildChild(parent: String, childName: String): Expr = ???

  override def buildChildOpt(
    parent: String,
    childName: String
  ): Option[fauna.tool.ast.Expr] = ???

  def buildChild(parent: Expr, childName: String): Expr =
    bf.buildChild(parent, childName)

  override def extractLiteral(value: String): Literal = ???

  override def matchKeys(value: String, classAccessors: Accessors): Boolean = ???

  override def buildOpt(value: String): Option[Expr] = ???

  override def build(value: String): Expr = bf.build(parseT(value))

  def parseT(s: String): Expr = fastparse.parse(s, FQL.EXPRESSION(_)).get.value
}

private class FQL_ASTBuilder extends ASTBuilder[Expr] {

  def findBuilder(q: FQL.MethodCall): Option[(Builder, Accessors, Arity)] = {
    registered.collectFirst {
      case (name, Arity.Exact(n), acc, builder)
          if (q.name == name && q.arguments.size == n) =>
        (builder, acc, Arity.Exact(n))
      case (name, Arity.VarArgs, acc, builder)
          if (q.name == name && q.arguments.length >= 1) =>
        (builder, acc, Arity.VarArgs)
      case (name, Arity.Between(min, max), acc, builder)
          if (q.name == name && q.arguments.size >= min && q.arguments.size <= max) =>
        (builder, acc)
    }
  }

  override def matchKeys(value: Expr, classAccessors: Accessors): Boolean = ???

  override def build(e: Expr): Expr = e match {
    case q: FQL.MethodCall => {
      val d: Expr = Undefined
      findBuilder(q).fold(d)(_._1(q))
    }
    case l: Literal => l
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
            case _             => Some(mc)
          }
        case None => None
      }
    case e: Expr => Some(e)
  }

  override def buildOpt(value: Expr): Option[Expr] = ???

  override def extractLiteral(value: Expr): Literal = value match {
    case l: Literal => l
  }

  def paramToIndex(
    param: String,
    builder: Option[(Builder, Accessors, Arity)]
  ): Option[Int] = {
    val d: Option[Int] = None
    builder.fold(d) {
      case (_: Builder, acc: Accessors, _: Arity) => {
        val i = acc.indexWhere(_._1 == param)
        if (i == -1) None else Some(i)
      }
    }
  }

  def indexToParam(
    i: Int,
    builder: Option[(Builder, Accessors, Arity)]
  ): Option[String] = {
    val d: Option[String] = None
    builder.fold(d) {
      case (_: Builder, acc: Accessors, _: Arity) => {
        if (i <= acc.size) Some(acc(i)._1) else None
      }
    }
  }

  def readArgument(q: FQL.MethodCall, param: String): Option[Expr] = {

    val accum: Option[Expr] = None
    val builder: Option[(Builder, Accessors, Arity)] = findBuilder(q)
    builder match {
      case Some((_, _, Arity.VarArgs)) => Some(ArrayL(q.arguments.toList))
      case _ =>
        paramToIndex(param, builder).fold(accum)((idx: Int) => {
          if (idx < q.arguments.length) Some(q.arguments(idx)) else None
        })
    }
  }

}

private object FQL {
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
    P(NUMBER | STRING | BOOLEAN | LIST | NULL | OBJECT | CALL)

  def kvPair[_: P]: P[(String, Expr)] =
    P(P(Lexical.identifier | Lexical.stringliteral) ~ ":" ~ EXPRESSION)

  def OBJECT[_: P]: P[ObjectL] = P("{" ~ kvPair.rep(0, sep = ",") ~ "}").map { seq =>
    ObjectL(seq.toMap)
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
