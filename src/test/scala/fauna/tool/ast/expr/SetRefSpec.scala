package fauna.tool.ast

class SetRefFunctionSpec extends ExprSuite {

// Set ref constructors
  test("Singleton()") {
    val expr = Singleton(singleton = NullL)
    assertExpr(expr, "Singleton", 1, Arity.Exact(1), Effect.Pure)

    assertBuildFromWire(
      """{"singleton":{"@ref":{"collection":"posts","scope":"233286601218720256"}}}""",
      Singleton(
        Ref(Collection(StringL("posts"), Some(StringL("233286601218720256"))))
      )
    )
  }

  test("Events()") {
    val expr = Events(events = NullL)
    assertExpr(expr, "Events", 1, Arity.Exact(1), Effect.Pure)

    assertBuildFromWire(
      """{"events": {"collection": "coll"}}""",
      Events(Collection(StringL("coll"), None))
    )

    assertBuildFromCode(
      """Events(Collection("coll1"))""",
      Events(Collection(StringL("coll1"), None))
    )
  }

  abstract class AbstractMatch(
    `match`: Expr,
    index: Option[Expr],
    terms: Option[Expr]
  ) extends FnExpr

  test("Match()") {
    val expr = Match(`match` = NullL)
    assertExpr(expr, "Match", 1, Arity.Exact(1), Effect.Pure)
  }

  test("MatchIndex()") {
    val expr = MatchIndex(`match` = NullL, index = NullL)
    assertExpr(expr, "MatchIndex", 2, Arity.Exact(2), Effect.Pure)
  }

  test("MatchTerms()") {
    val expr = MatchTerms(`match` = NullL, terms = NullL)
    assertExpr(expr, "MatchTerms", 2, Arity.Exact(2), Effect.Pure)
  }

  test("Union()") {
    val expr = Union(union = NullL)
    assertExpr(expr, "Union", 1, Arity.Exact(1), Effect.Pure)

  }

  test("Intersection()") {
    val expr = Intersection(intersection = NullL)
    assertExpr(expr, "Intersection", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Difference()") {
    val expr = Difference(difference = NullL)
    assertExpr(expr, "Difference", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Distinct()") {
    val expr = Distinct(distinct = NullL)
    assertExpr(expr, "Distinct", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Range()") {
    val expr = Range(range = NullL, from = NullL, to = NullL)
    assertExpr(expr, "Range", 3, Arity.Exact(3), Effect.Pure)
  }

}
