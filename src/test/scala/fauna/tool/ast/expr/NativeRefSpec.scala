package fauna.tool.ast

import org.scalatest._

class NativeRefFunctionsSpec extends ExprSuite {

// Native classref constructors
  test("Databases()") {
    val expr = Databases(databases = NullL)
    val json = """{(databases = NullL)}"""

    assertExpr(expr, "Databases", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Indexes()") {
    val expr = Indexes(indexes = NullL)
    val json = """{(indexes = NullL)}"""

    assertExpr(expr, "Indexes", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Classes()") {
    val expr = Classes(classes = NullL)
    val json = """{(classes = NullL)}"""

    assertExpr(expr, "Classes", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Collections()") {
    val expr = Collections(collections = NullL)
    val json = """{(collections = NullL)}"""

    assertExpr(expr, "Collections", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Keys()") {
    val expr = Keys(keys = NullL)
    val json = """{(keys = NullL)}"""

    assertExpr(expr, "Keys", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Tokens()") {
    val expr = Tokens(tokens = NullL)
    val json = """{(tokens = NullL)}"""

    assertExpr(expr, "Tokens", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Credentials()") {
    val expr = Credentials(credentials = NullL)
    val json = """{(credentials = NullL)}"""

    assertExpr(expr, "Credentials", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Functions()") {
    val expr = Functions(functions = NullL)
    val json = """{(functions = NullL)}"""

    assertExpr(expr, "Functions", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Roles()") {
    val expr = Roles(roles = NullL)
    val json = """{(roles = NullL)}"""

    assertExpr(expr, "Roles", 1, Arity.Exact(1), Effect.Pure)
  }

}
