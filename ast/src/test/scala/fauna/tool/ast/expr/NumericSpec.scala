package fauna.tool.ast

import org.scalatest._

class NumericFunctionsSpec extends ExprSuite {

//Numerics
  test("Abs()") {
    val expr = Abs(abs = NullL)

    assertExpr(expr, "Abs", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Add()") {
    val expr = Add(add = NullL)

    assertExpr(expr, "Add", 1, Arity.VarArgs, Effect.Pure)
    expr.toJson
  }

  test("BitAnd()") {
    val expr = BitAnd(bitand = NullL)

    assertExpr(expr, "BitAnd", 1, Arity.VarArgs, Effect.Pure)
  }

  test("BitNot()") {
    val expr = BitNot(bitnot = NullL)

    assertExpr(expr, "BitNot", 1, Arity.VarArgs, Effect.Pure)
  }

  test("BitOr()") {
    val expr = BitOr(bitor = NullL)

    assertExpr(expr, "BitOr", 1, Arity.VarArgs, Effect.Pure)
  }

  test("BitXor()") {
    val expr = BitXor(bitxor = NullL)

    assertExpr(expr, "BitXor", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Ceil()") {
    val expr = Ceil(ceil = NullL)

    assertExpr(expr, "Ceil", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Divide()") {
    val expr = Divide(divide = NullL)

    assertExpr(expr, "Divide", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Floor()") {
    val expr = Floor(floor = NullL)

    assertExpr(expr, "Floor", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Max()") {
    val expr = Max(max = NullL)

    assertExpr(expr, "Max", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Min()") {
    val expr = Min(min = NullL)

    assertExpr(expr, "Min", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Modulo()") {
    val expr = Modulo(modulo = NullL)

    assertExpr(expr, "Modulo", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Multiply()") {
    val expr = Multiply(multiply = NullL)

    assertExpr(expr, "Multiply", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Round()") {
    val expr = Round(round = NullL, precision = Some(NullL))

    assertExpr(expr, "Round", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Subtract()") {
    val expr = Subtract(subtract = NullL)

    assertExpr(expr, "Subtract", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Sign()") {
    val expr = Sign(sign = NullL)

    assertExpr(expr, "Sign", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Sqrt()") {
    val expr = Sqrt(sqrt = NullL)

    assertExpr(expr, "Sqrt", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Trunc()") {
    val expr = Trunc(trunc = NullL, precision = Some(NullL))

    assertExpr(expr, "Trunc", 2, Arity.Between(1, 2), Effect.Pure)
  }

  test("Mean()") {
    val expr = Mean(mean = NullL)

    assertExpr(expr, "Mean", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Count()") {
    val expr = Count(count = NullL)

    assertExpr(expr, "Count", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Sum()") {
    val expr = Sum(sum = NullL)

    assertExpr(expr, "Sum", 1, Arity.VarArgs, Effect.Pure)
  }

  test("Any()") {
    val expr = Any(any = NullL)

    assertExpr(expr, "Any", 1, Arity.VarArgs, Effect.Pure)
  }

  test("All()") {
    val expr = All(all = NullL)

    assertExpr(expr, "All", 1, Arity.VarArgs, Effect.Pure)
  }

}
