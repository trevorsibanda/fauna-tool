package fauna.tool.ast

import org.scalatest._

class ComparisonFunctionsSpec extends ExprSuite {
  test("LT()") {
    val expr = LT(lt = NullL)

    assertExpr(expr, "LT", 1, Arity.Exact(1), Effect.Pure)
  }

  test("LTE()") {
    val expr = LTE(lte = NullL)

    assertExpr(expr, "LTE", 1, Arity.Exact(1), Effect.Pure)
  }

  test("GT()") {
    val expr = GT(gt = NullL)

    assertExpr(expr, "GT", 1, Arity.VarArgs, Effect.Pure)
  }

  test("GTE()") {
    val expr = GTE(gte = NullL)

    assertExpr(expr, "GTE", 1, Arity.VarArgs, Effect.Pure)
  }

}
