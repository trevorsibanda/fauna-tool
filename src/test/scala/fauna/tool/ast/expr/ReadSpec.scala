package fauna.tool.ast

import org.scalatest._

class ReadFunctionsSpec extends ExprSuite {

  test("Exists()") {
    val expr = Exists(exists = NullL, ts = Some(NullL))

    assertExpr(expr, "Exists", 2, Arity.Between(1, 2), Effect.Read)

    assertBuildFromWire(
      """{"exists": {"database": "testdb"}}""",
      Exists(Database(StringL("testdb"), None), None)
    )
    assertBuildFromWire(
      """{"exists": {"database": "testdb"}, "ts": 0}""",
      Exists(Database(StringL("testdb"), None), Some(IntL(0)))
    )

    assertBuildFromCode(
      """Exists(Database("test-db"), 9090)""",
      Exists(Database(StringL("test-db"), None), Some(IntL(9090)))
    )
  }

  test("Get()") {
    val expr = Get(get = NullL, ts = Some(NullL))

    assertExpr(expr, "Get", 2, Arity.Between(1, 2), Effect.Read)

    assertBuildFromWire(
      """{"get": {"database": "testdb"}}""",
      Get(Database(StringL("testdb"), None), None)
    )
    assertBuildFromWire(
      """{"get": {"database": "testdb"}, "ts": 0}""",
      Get(Database(StringL("testdb"), None), Some(IntL(0)))
    )

    assertBuildFromCode(
      """Exists(Database("test-db"), 9090)""",
      Exists(Database(StringL("test-db"), None), Some(IntL(9090)))
    )
  }

  test("KeyFromSecret()") {
    val expr = KeyFromSecret(key_from_secret = NullL)

    assertExpr(expr, "KeyFromSecret", 1, Arity.Exact(1), Effect.Read)

    val expected = KeyFromSecret(StringL("fnADc2QyWNACAJw40Yd1xxtb31KV2OaKpIlxK1am"))

    assertBuildFromWire(
      """{"key_from_secret": "fnADc2QyWNACAJw40Yd1xxtb31KV2OaKpIlxK1am"}""",
      expected
    )

    assertBuildFromCode(
      """KeyFromSecret("fnADc2QyWNACAJw40Yd1xxtb31KV2OaKpIlxK1am")""",
      expected
    )
  }

  test("Paginate()") {
    case class PaginateCursor(
      paginate: Expr,
      cursor: Expr,
      ts: Option[Expr],
      size: Option[Expr],
      events: Option[Expr],
      sources: Option[Expr]
    )
    case class PaginateBefore(
      paginate: Expr,
      before: Expr,
      ts: Option[Expr],
      size: Option[Expr],
      events: Option[Expr],
      sources: Option[Expr]
    )
    case class PaginateAfter(
      paginate: Expr,
      after: Option[Expr],
      ts: Option[Expr],
      size: Option[Expr],
      events: Option[Expr],
      sources: Option[Expr]
    )

  }
}
