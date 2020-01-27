package fauna.tool.codegen

import fauna.tool.ast.{ Encoder, Expr }

class CurlGenerator(jsonEncoder: fauna.tool.ast.Serializer[_]) extends Generator {
  val name = "CurlGenerator"

  val allowUnknownExpr: Boolean = true

  val template =
    """curl %s -H "X-Fauna-Driver: fauna-tool" -H "Authorization: Basic %s" -d '%s' # %s"""

  val commentGen = new JSCodeGenerator()

  override def exprToCode(expr: Expr): Code = {
    val jsonString = expr.encode(jsonEncoder)
    template.format(
      "$FAUNA_HOST",
      "$FAUNA_KEY",
      jsonString,
      commentGen.exprToCode(expr)
    )
  }

  override def code: String = ""

}
