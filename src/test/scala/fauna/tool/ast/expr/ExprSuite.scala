package fauna.tool.ast

import org.scalatest._
import fauna.tool.parser.JsonASTBuilder
import fauna.tool.parser.FQLBuilder
import org.json4s.JsonAST.JValue

import org.json4s._
import org.json4s.native.JsonMethods._
import fauna.tool.parser.FQL_ASTBuilder

class ExprSuite extends FunSuite with Matchers {

  implicit val jsonDefaultFormats = DefaultFormats

  def assertExpr(
    expr: Expr,
    name: String,
    children: Int,
    arity: Arity,
    effect: Effect
  ) = {
    expr.name shouldEqual name
    expr.children.length shouldEqual children
    expr.effect shouldEqual effect
    expr.arity shouldEqual arity
  }

  def assertBuildFromWire(json: String, expected: Expr): Unit = {
    assertBuildFromWire(parse(json), expected)
  }

  def assertBuildFromWire(json: JValue, expected: Expr): Unit = {
    expected.build(json)(ExprSuite.jsonAST) shouldEqual expected
  }

  def assertBuildFromCode(fql: String, expected: Expr) = {
    ExprSuite.fqlAST.build(fql) shouldEqual expected
  }

  def assertConvertToJson(expr: Expr, expected: JValue) = {}

  def assertConvertToJson(expr: Expr, expected: String) = {}

  def assertConvertToFQL(expr: Expr, expected: String) = {}

}

object ExprSuite {
  private[ast] implicit val jsonAST = new JsonASTBuilder()
  Expr.reg(jsonAST)

  private[ast] implicit val fqlAST = new FQLBuilder()
  Expr.reg(fqlAST)

}
