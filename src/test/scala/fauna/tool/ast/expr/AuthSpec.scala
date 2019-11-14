package fauna.tool.ast

class AuthFunctionsSpec extends ExprSuite {

  test("Identity()") {
    val expr = new Identity(NullL)

    assertExpr(expr, "Identity", 1, Arity.Exact(1), Effect.Pure)

    assertBuildFromCode("Identity()", Identity(NullL))
    assertBuildFromWire("""{"identity": null}""", Identity(NullL))

  }

  test("HasIdentity()") {
    val expr = HasIdentity(has_identity = NullL)

    assertExpr(expr, "HasIdentity", 1, Arity.Exact(1), Effect.Pure)

    assertBuildFromCode("HasIdentity()", HasIdentity(NullL))
    assertBuildFromWire("""{"has_identity": null}""", HasIdentity(NullL))
  }

  test("Identify()") {
    val expr = Identify(identify = NullL, password = NullL)

    assertExpr(expr, "Identify", 2, Arity.Exact(2), Effect.Pure)
    assertBuildFromCode(
      """Identify(Collection("testcol"), "secret")""",
      Identify(Collection(StringL("testcol"), None), StringL("secret"))
    )
  }

  test("Login()") {
    val expr = Login(login = NullL, params = NullL)

    assertExpr(expr, "Login", 2, Arity.Exact(2), Effect.Pure)

    assertBuildFromCode(
      """Login(Collection("testcol"), "password")""",
      Login(Collection(StringL("testcol"), None), StringL("password"))
    )
    assertBuildFromWire(
      """{"login": {"collection": "testcol"}, "params": "password"}""",
      Login(Collection(StringL("testcol"), None), StringL("password"))
    )
  }

  test("Logout()") {
    val expr = Logout(logout = NullL)
    assertExpr(expr, "Logout", 1, Arity.Exact(1), Effect.Pure)

    assertBuildFromCode("Logout()", Logout(NullL))
    assertBuildFromWire("{\"logout\": null}", Logout(NullL))
  }

}
