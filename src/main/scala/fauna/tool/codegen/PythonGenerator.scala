package fauna.tool.codegen

import fauna.tool.ast._
import fauna.tool.parser.ASTBuilder

class PythonCodeGenerator extends Generator {
  override val name = "PythonCodeGenerator"

  val packagePrefix: String = "q."

  override val allowUnknownExpr: Boolean = true

  override def fnName(fn: String): String = s"${packagePrefix}${camelToSnake(fn)}"

  override def exprToCode(expr: Expr): Code = expr match {
    case UnknownExpression(jvalue) => {
      if (!allowUnknownExpr) {
        throw new GeneratorException(
          "Failed to generate code. Found UnknownExpression"
        )
      } else {
        blockComment(s"UnknownExpression($jvalue)")
      }
    }
    case If(i, t, e) =>
      s"${fnName("if_")}(${exprToCode(i)}, ${exprToCode(t)}, ${exprToCode(e)})"
    case Or(ors)  => s"${fnName("or_")}(${exprToCode(ors)})"
    case And(and) => s"${fnName("and_")}(${exprToCode(and)})"
    case Class(cls, scope) =>
      s"""${fnName("class_")}(${exprToCode(cls)}${scope.fold("")(
        x => s", ${exprToCode(x)}"
      )})"""
    case Filter(collection, filter) =>
      s"${fnName("filter_")}(${exprToCode(collection)}, ${exprToCode(filter)})"
    case fv: FaunaValue => faunaValueToCode(fv)
    case l: Literal     => literalToCode(l)
    case e: Expr        => super.exprToCode(e)
  }

  override def literalToCode(l: Literal): String = l match {
    case NullL     => s"None"
    case TrueL     => "True"
    case FalseL    => "False"
    case ArrayL(l) => l.map(exprToCode).mkString(s"[", ", ", "]")
    case ObjectL(m) =>
      m.map {
          case (k, v) => s""""$k": ${exprToCode(v)}"""
        }
        .mkString(s"{", ", ", "}")
    case _ => super.literalToCode(l)
  }

  def camelToSnake(name: String) = {
    "_?[A-Z][a-z\\d]+".r
      .findAllMatchIn(name)
      .map(_.group(0).toLowerCase)
      .mkString("_")
  }

  override def code: Code = ???

  override implicit val builder: ASTBuilder[_] =
    new fauna.tool.parser.JsonASTBuilder()

}
