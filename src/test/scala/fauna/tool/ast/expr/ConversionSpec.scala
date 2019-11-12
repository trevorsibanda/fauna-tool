package fauna.tool.ast

import org.scalatest._

class ConversionFunctionsSpec extends ExprSuite {

//conversion
  test("ToString()") {
    val expr = ToString(to_string = NullL)
    val json = """{(to_string = NullL)}"""

    assertExpr(expr, "ToString", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ToNumber()") {
    val expr = ToNumber(to_number = NullL)
    val json = """{(to_number = NullL)}"""

    assertExpr(expr, "ToNumber", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ToTime()") {
    val expr = ToTime(to_time = NullL)
    val json = """{(to_time = NullL)}"""

    assertExpr(expr, "ToTime", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ToDate()") {
    val expr = ToDate(to_date = NullL)
    val json = """{(to_date = NullL)}"""

    assertExpr(expr, "ToDate", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ToObject()") {
    val expr = ToObject(to_object = NullL)
    val json = """{(to_object = NullL)}"""

    assertExpr(expr, "ToObject", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ToArray()") {
    val expr = ToArray(to_array = NullL)
    val json = """{(to_array = NullL)}"""

    assertExpr(expr, "ToArray", 1, Arity.Exact(1), Effect.Pure)
  }

}
