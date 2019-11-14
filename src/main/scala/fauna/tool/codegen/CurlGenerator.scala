package fauna.tool.codegen

import fauna.tool.ast._
import fauna.tool.parser.ASTBuilder

import org.json4s._
import org.json4s.native.JsonMethods._

class CurlGenerator extends Generator {
  val name = "CurlGenerator"

  val allowUnknownExpr: Boolean = true

  val template =
    """curl %s -H "X-Fauna-Driver: fauna-tool" -H "Authorization: Basic %s" -d '%s' # %s"""

  val commentGen = new JSCodeGenerator()

  implicit val jsonDefaultFormats = DefaultFormats

  override def exprToCode(expr: Expr): Code = {
    val json = expr.toJson
    val jsonString = compact(render(json))
    template.format(
      "$FAUNA_HOST",
      "$BASE64_FAUNA_KEY",
      jsonString,
      commentGen.exprToCode(expr)
    )
  }

  override val builder: ASTBuilder[_] = null

  override def code: String = ""

}
