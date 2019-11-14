package fauna.tool.ast

import org.scalatest._

class CollectionFunctionsSpec extends ExprSuite {

//collection
  test("Prepend()") {
    val expr = Prepend(prepend = NullL, collection = NullL)

    assertExpr(expr, "Prepend", 2, Arity.Exact(2), Effect.Pure)

    assertBuildFromCode(
      "Prepend([1,2,3], [4,5,6])",
      Prepend(ArrayL(IntL(1), IntL(2), IntL(3)), ArrayL(IntL(4), IntL(5), IntL(6)))
    )
  }

  test("Append()") {
    val expr = Append(append = NullL, collection = NullL)

    assertExpr(expr, "Append", 2, Arity.Exact(2), Effect.Pure)
  }

  test("Take()") {
    val expr = Take(take = NullL, collection = NullL)

    assertExpr(expr, "Take", 2, Arity.Exact(2), Effect.Pure)
  }

  test("Drop()") {
    val expr = Drop(drop = NullL, collection = NullL)

    assertExpr(expr, "Drop", 2, Arity.Exact(2), Effect.Pure)
  }

  test("IsEmpty()") {
    val expr = IsEmpty(is_empty = NullL)

    assertExpr(expr, "IsEmpty", 1, Arity.Exact(1), Effect.Pure)
  }

  test("IsNonEmpty()") {
    val expr = IsNonEmpty(is_nonempty = NullL)

    assertExpr(expr, "IsNonEmpty", 1, Arity.Exact(1), Effect.Pure)
  }

}
