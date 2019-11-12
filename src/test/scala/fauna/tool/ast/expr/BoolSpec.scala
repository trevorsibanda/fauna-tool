package fauna.tool.ast

import org.scalatest._

class BoolFunctionsSpec extends ExprSuite {

  test("And()") {
    val expr = And(and = NullL)
    val json = """{(and = NullL)}"""

    assertExpr(expr, "And", 1, Arity.Exact(1), Effect.Pure)

    assertBuildFromWire("""{"and":true}""", And(TrueL))
    assertBuildFromWire(
      """{"and":[true,false,true]}""",
      And(ArrayL(List(TrueL, FalseL, TrueL)))
    )

    assertBuildFromCode(
      "And([true, false, true])",
      And(ArrayL(List(TrueL, FalseL, TrueL)))
    )
    assertBuildFromCode("And(Not(true))", And(Not(TrueL)))

  }

  test("Or()") {
    val expr = Or(or = NullL)
    val json = """{(or = NullL)}"""

    assertExpr(expr, "Or", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Not()") {
    val expr = Not(not = NullL)
    val json = """{(not = NullL)}"""

    assertExpr(expr, "Not", 1, Arity.Exact(1), Effect.Pure)
  }

}
