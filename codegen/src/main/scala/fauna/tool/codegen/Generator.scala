package fauna.tool.codegen

import fauna.tool.ast._

import scala.util.Try

case class GeneratorException(msg: String) extends Exception(msg)

trait Generator {
  type Code = String
  type CodeBuilder = StringBuilder

  val name: String

  val allowUnknownExpr: Boolean

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
    case SetV(s)        => s"""${fnName("Set")}(${exprToCode(s)})"""
    case DateV(v)       => s"""${fnName("Date")}(${exprToCode(v)})"""
    case BytesV(b)      => s"""${fnName("Bytes")}(${exprToCode(b)})"""
    case TimestampV(ts) => s"""${fnName("Timestamp")}(${exprToCode(ts)})"""
    case QueryV(q)      => s"""${fnName("Query")}(${exprToCode(q)})"""
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
    case e @ (Databases(NullL) | Collections(NullL) | Classes(NullL) |
        Indexes(NullL) | Keys(NullL) | Tokens(NullL) | Functions(NullL) |
        Logout(NullL) | NewID(NullL) | NextID(NullL) | AccessProviders(NullL)) =>
      s"${fnName(e.name)}()"
    case fv: FaunaValue => faunaValueToCode(fv)
    case l: Literal     => literalToCode(l)
    case e: Expr if e.arity.isInstanceOf[Arity.Exact] =>
      e.children
        .map {
          case Some(x) => exprToCode(x)
        }
        .mkString(s"${fnName(e.name)}(", ",", ")")

    case e: Expr if e.arity.isInstanceOf[Arity.Between] =>
      e.children
        .collect {
          case Some(x) => exprToCode(x)
        }
        .mkString(s"${fnName(e.name)}(", ",", ")")
    case e: Expr if e.arity == Arity.VarArgs =>
      e.children
        .map {
          case Some(x) => exprToCode(x)
        }
        .mkString(s"${fnName(e.name)}(", ",", ")")

  }

  def fnName(fn: String): String = fn

  def code: Code

}
