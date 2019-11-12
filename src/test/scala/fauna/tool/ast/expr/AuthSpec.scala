package fauna.tool.ast

import org.scalatest._

import fauna.tool.parser.ASTBuilder

class AuthFunctionsSpec extends ExprSuite {

  test("Identity()") {
    val expr = new Identity(NullL)
    val json = """{"identity": null}"""

    assertExpr(expr, "Identity", 1, Arity.Exact(1), Effect.Pure)

  }

  test("HasIdentity()") {
    val expr = HasIdentity(has_identity = NullL)
    val json = """{(has_identity: Expr)}"""

    assertExpr(expr, "HasIdentity", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Identify()") {
    val expr = Identify(identify = NullL, password = NullL)
    val json = """{(identify: Expr, password: Expr)}"""

    assertExpr(expr, "Identify", 2, Arity.Exact(2), Effect.Pure)
  }

  test("Login()") {
    val expr = Login(login = NullL, params = NullL)
    val json = """{(login: Expr, params: Expr)}"""

    assertExpr(expr, "Login", 2, Arity.Exact(2), Effect.Pure)
  }

  test("Logout()") {
    val expr = Logout(logout = NullL)
    val json = """{(logout: Expr)}"""

    assertExpr(expr, "Logout", 1, Arity.Exact(1), Effect.Pure)
  }

}
