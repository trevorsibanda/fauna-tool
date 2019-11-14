package fauna.tool.ast

class BasicFunctionsSpec extends ExprSuite {

  test("Body()") {
    val expr = Body(expr = NullL)
    assertExpr(expr, "Body", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Query()") {
    val expr = QueryV(NullL)
    assertExpr(expr, "QueryV", 1, Arity.Exact(1), Effect.Pure)

    //val e = QueryV(Lambda(StringL("x"), Var(StringL("x"))))
    //assertBuildFromCode("Query(Lambda('x',Var('x')))", e)
    //assertBuildFromWire("""{"@query":{"lambda":"x","expr":{"var":"x"}}}""", e)
  }

  test("Lambda()") {
    val expr = Lambda(lambda = NullL, expr = NullL)
    assertExpr(expr, "Lambda", 2, Arity.Exact(2), Effect.Pure)

    assertBuildFromCode(
      """Lambda(["x", "y"], Var("x"))""",
      Lambda(ArrayL(List(StringL("x"), StringL("y"))), Var(StringL("x")))
    )
    assertBuildFromWire(
      """{"lambda": "x", "expr": {"var": "x"}}""",
      Lambda(StringL("x"), Var(StringL("x")))
    )
  }

  test("Call()") {
    val expr = Call(call = NullL, arguments = Some(NullL))
    assertExpr(expr, "Call", 2, Arity.Between(1, 2), Effect.Pure)

    assertBuildFromCode("Call('user_fn')", Call(StringL("user_fn"), None))
    assertBuildFromCode(
      "Call('user_fn2', {x: 1, y: 2})",
      Call(StringL("user_fn2"), Some(ObjectL(Map("x" -> IntL(1), "y" -> IntL(2)))))
    )

    assertBuildFromWire("""{"call": "user_fn"}""", Call(StringL("user_fn"), None))
    assertBuildFromWire(
      """{"call": "user_fn", "arguments": [1,2,3]}""",
      Call(StringL("user_fn"), Some(ArrayL(IntL(1), IntL(2), IntL(3))))
    )
  }

  test("Join()") {
    val expr = Join(join = NullL, `with` = NullL)
    assertExpr(expr, "Join", 2, Arity.Exact(2), Effect.Pure)
  }

  test("ForEach()") {
    val expr = ForEach(foreach = NullL, collection = NullL)
    assertExpr(expr, "ForEach", 2, Arity.Exact(2), Effect.Pure)

    assertBuildFromCode(
      "ForEach([1,2,3,4,5], Lambda('c', Var('c')))",
      ForEach(
        ArrayL(List(IntL(1), IntL(2), IntL(3), IntL(4), IntL(5))),
        Lambda(StringL("c"), Var(StringL("c")))
      )
    )
    assertBuildFromWire(
      """{"foreach": {"lambda": "d", "expr": {"add": [{"var": "d"}, 2]}}, "collection": [1,2,3,4,5]}""",
      ForEach(
        Lambda(StringL("d"), Add(ArrayL(List(Var(StringL("d")), IntL(2))))),
        ArrayL(List(IntL(1), IntL(2), IntL(3), IntL(4), IntL(5)))
      )
    )
  }

  test("Filter()") {
    val expr = Filter(filter = NullL, collection = NullL)
    assertExpr(expr, "Filter", 2, Arity.Exact(2), Effect.Pure)

    assertBuildFromCode(
      """Filter([1,2,3,4,5], Lambda("x", LTE([Var("x"), 2])))""",
      Filter(
        ArrayL(List(IntL(1), IntL(2), IntL(3), IntL(4), IntL(5))),
        Lambda(StringL("x"), LTE(ArrayL(Var(StringL("x")), IntL(2))))
      )
    )
    assertBuildFromWire(
      """{"filter":{"lambda":"a","expr":{"equals":[{"modulo":[{"var":"a"},2]},0]}},"collection":[1,2,3,4]}""",
      Filter(
        ArrayL(IntL(1), IntL(2), IntL(3), IntL(4)),
        Lambda(
          StringL("a"),
          Equals(ArrayL(Modulo(ArrayL(Var(StringL("a")), IntL(2))), IntL(0)))
        )
      )
    )
  }

  test("MapFn()") {
    val expr = MapFn(collection = NullL, map = NullL)
    assertExpr(expr, "Map", 2, Arity.Exact(2), Effect.Pure)

    assertBuildFromCode(
      """Map([1, 2, 3],Lambda("a",Multiply(2,Var("a"))))""",
      MapFn(
        ArrayL(IntL(1), IntL(2), IntL(3)),
        Lambda(StringL("a"), Multiply(ArrayL(IntL(2), Var(StringL("a")))))
      )
    )
    assertBuildFromWire(
      """{"map":{"lambda":"a","expr":{"multiply":[2,{"var":"a"}]}},"collection":[1,2,3]}""",
      MapFn(
        ArrayL(IntL(1), IntL(2), IntL(3)),
        Lambda(StringL("a"), Multiply(ArrayL(IntL(2), Var(StringL("a")))))
      )
    )
  }

  test("Do()") {
    val expr = Do(`do` = NullL)
    assertExpr(expr, "Do", 1, Arity.Exact(1), Effect.Pure)

    assertBuildFromCode("Do([1, 2])", Do(ArrayL(IntL(1), IntL(2))))
    assertBuildFromWire(
      """{"do":[{"delete":{"database": "nothing"}},1]}""",
      Do(ArrayL(Delete(Database(StringL("nothing"), None)), IntL(1)))
    )
  }

  test("If()") {
    val expr = If(`if` = NullL, then = NullL, `else` = NullL)
    assertExpr(expr, "If", 3, Arity.Exact(3), Effect.Pure)

    assertBuildFromCode(
      """If(false,"t","f")""",
      If(FalseL, StringL("t"), StringL("f"))
    )
    assertBuildFromWire(
      """{"if":true,"then":"t","else":"f"}""",
      If(TrueL, StringL("t"), StringL("f"))
    )
  }

  test("Let()") {
    val expr = Let(let = NullL, in = NullL)
    assertExpr(expr, "Let", 2, Arity.Exact(2), Effect.Pure)

    assertBuildFromCode(
      "Let({'x': [2, 3, 5]},Add([Var('x')]))",
      Let(
        ObjectL(Map("x" -> ArrayL(List(IntL(2), IntL(3), IntL(5))))),
        Add(ArrayL(List(ArrayL(List(Var(StringL("x")))))))
      )
    )
    assertBuildFromWire(
      """{"let":[{"x":1}],"in":{"var":"x"}}""",
      Let(ArrayL(ObjectL(Map("x" -> IntL(1)))), Var(StringL("x")))
    )
  }

  test("Var()") {
    val expr = Var(NullL)
    assertExpr(expr, "Var", 1, Arity.Exact(1), Effect.Pure)

    assertBuildFromCode("Var('x')", Var(StringL("x")))
    assertBuildFromWire("""[{"var": "v"}]""", ArrayL(List(Var(StringL("v")))))
  }

  test("At()") {
    val expr = At(at = NullL, expr = NullL)
    assertExpr(expr, "At", 2, Arity.Exact(2), Effect.Pure)
  }

  test("Merge()") {
    val expr = Merge(merge = NullL, `with` = NullL, lambda = Some(NullL))
    assertExpr(expr, "Merge", 3, Arity.Between(2, 3), Effect.Pure)
  }

  test("Reduce()") {
    val expr = Reduce(reduce = NullL, initial = NullL, collection = NullL)
    assertExpr(expr, "Reduce", 3, Arity.Exact(3), Effect.Pure)
  }

  test("Abort()") {
    val expr = Abort(abort = NullL)
    assertExpr(expr, "Abort", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Equals()") {
    val expr = Equals(equals = NullL)
    assertExpr(expr, "Equals", 1, Arity.Exact(1), Effect.Pure)
  }

  test("Contains()") {
    val expr = Contains(contains = NullL, in = NullL)
    assertExpr(expr, "Contains", 2, Arity.Exact(2), Effect.Pure)
  }

  test("Select()") {
    val expr = Select(select = NullL, from = NullL, default = Some(NullL))
    assertExpr(expr, "Select", 3, Arity.Between(2, 3), Effect.Read)
  }

  /*
case class SelectAll(select_all = NullL, from = NullL, default: Option[Expr])

case class SelectAsIndex(select_as_index = NullL, from = NullL, default: Option[Expr])
 */

}
