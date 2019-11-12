package fauna.tool.ast

class TrigFunctionsSpec extends ExprSuite {

//trig
  test("ACos()") {
    val expr = ACos(acos = NullL)
    assertExpr(expr, "ACos", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ASin()") {
    val expr = ASin(asin = NullL)
    assertExpr(expr, "ASin", 1, Arity.Exact(1), Effect.Pure)
  }

  test("ATan()") {
    val expr = ATan(atan = NullL)
    assertExpr(expr, "ATan", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Cos()") {
    val expr = Cos(cos = NullL)
    assertExpr(expr, "Cos", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Cosh()") {
    val expr = Cosh(cosh = NullL)
    assertExpr(expr, "Cosh", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Degrees()") {
    val expr = Degrees(degrees = NullL)
    assertExpr(expr, "Degrees", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Exp()") {
    val expr = Exp(exp = NullL)
    assertExpr(expr, "Exp", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Hypot()") {
    val expr = Hypot(hypot = NullL, b = Some(NullL))
    assertExpr(expr, "Hypot", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Ln()") {
    val expr = Ln(ln = NullL)
    assertExpr(expr, "Ln", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Log()") {
    val expr = Log(log = NullL)
    assertExpr(expr, "Log", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Pow()") {
    val expr = Pow(pow = NullL, exp = Some(NullL))
    assertExpr(expr, "Pow", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Radians()") {
    val expr = Radians(radians = NullL)
    assertExpr(expr, "Radians", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Sin()") {
    val expr = Sin(sin = NullL)
    assertExpr(expr, "Sin", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Sinh()") {
    val expr = Sinh(sinh = NullL)
    assertExpr(expr, "Sinh", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Tan()") {
    val expr = Tan(tan = NullL)
    assertExpr(expr, "Tan", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Tanh()") {
    val expr = Tanh(tanh = NullL)
    assertExpr(expr, "Tanh", 1, Arity.Exact(1), Effect.Pure)
  }

}
