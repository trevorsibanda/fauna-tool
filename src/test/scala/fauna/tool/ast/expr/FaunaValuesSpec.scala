package fauna.tool.ast

class FaunaValuesSpec extends ExprSuite {

  trait FaunaValue extends Expr

  test("SetV()") {
    val expr = SetV(`@set` = NullL)
    assertExpr(expr, "SetV", 1, Arity.Exact(1), Effect.Pure)
  }

  test("BytesV()") {
    val expr = BytesV(`@bytes` = NullL)
    assertExpr(expr, "BytesV", 1, Arity.Exact(1), Effect.Pure)
  }

  test("QueryV()") {
    val expr = QueryV(`@query` = NullL)
    assertExpr(expr, "QueryV", 1, Arity.Exact(1), Effect.Pure)
  }

  test("DateV()") {
    val expr = DateV(`@date` = NullL)
    assertExpr(expr, "DateV", 1, Arity.Exact(1), Effect.Pure)
  }

  test("TimestampV()") {
    val expr = TimestampV(`@ts` = NullL)
    assertExpr(expr, "TimestampV", 1, Arity.Exact(1), Effect.Pure)
  }

}
