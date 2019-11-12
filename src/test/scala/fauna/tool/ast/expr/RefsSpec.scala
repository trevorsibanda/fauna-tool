package fauna.tool.ast

class RefsFunctionSpec extends ExprSuite {

  test("Ref()") {
    assertExpr(Ref(NullL), "Ref", 1, Arity.Exact(1), Effect.Pure)

  }

  test("Database()") {
    val expr = Database(NullL, None)

    assertExpr(expr, "Database", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Index()") {
    val expr = Index(NullL, None)

    assertExpr(expr, "Index", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Class()") {
    val expr = Class(NullL, None)

    assertExpr(expr, "Class", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Collection()") {
    val expr = Collection(NullL, None)

    assertExpr(expr, "Collection", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Function()") {
    val expr = Function(NullL, None)

    assertExpr(expr, "Function", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Role()") {
    val expr = Role(NullL, None)

    assertExpr(expr, "Role", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("NextID()") {
    val expr = NextID(NullL)

    assertExpr(expr, "NextID", 1, Arity.Exact(1), Effect.Pure)
  }

  test("NewID()") {
    val expr = NewID(NullL)

    assertExpr(expr, "NewID", 1, Arity.Exact(1), Effect.Pure)
  }

}
