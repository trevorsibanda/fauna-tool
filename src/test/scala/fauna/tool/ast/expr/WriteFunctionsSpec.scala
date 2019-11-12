package fauna.tool.ast

class WriteFunctionsSpec extends ExprSuite {

//writes
  test("Create()") {
    val expr = Create(create = NullL, params = Some(NullL))
    assertExpr(expr, "Create", 2, Arity.Between(1, 2), Effect.Write)
  }

  test("Update()") {
    val expr = Update(update = NullL, params = Some(NullL))
    assertExpr(expr, "Update", 2, Arity.Between(1, 2), Effect.Write)
  }

  test("Replace()") {
    val expr = Replace(replace = NullL, params = Some(NullL))
    assertExpr(expr, "Replace", 2, Arity.Between(1, 2), Effect.Write)
  }

  test("Delete()") {
    val expr = Delete(delete = NullL)
    assertExpr(expr, "Delete", 1, Arity.Exact(1), Effect.Write)
  }

  test("CreateClass()") {
    val expr = CreateClass(create_class = NullL)
    assertExpr(expr, "CreateClass", 1, Arity.Exact(1), Effect.Write)
  }

  test("CreateCollection()") {
    val expr = CreateCollection(create_collection = NullL)
    assertExpr(expr, "CreateCollection", 1, Arity.Exact(1), Effect.Write)
  }

  test("CreateDatabase()") {
    val expr = CreateDatabase(create_database = NullL)
    assertExpr(expr, "CreateDatabase", 1, Arity.Exact(1), Effect.Write)
  }

  test("CreateIndex()") {
    val expr = CreateIndex(create_index = NullL)
    assertExpr(expr, "CreateIndex", 1, Arity.Exact(1), Effect.Write)
  }

  test("CreateKey()") {
    val expr = CreateKey(create_key = NullL)
    assertExpr(expr, "CreateKey", 1, Arity.Exact(1), Effect.Write)
  }

  test("CreateFunction()") {
    val expr = CreateFunction(create_function = NullL)
    assertExpr(expr, "CreateFunction", 1, Arity.Exact(1), Effect.Write)
  }

  test("CreateRole()") {
    val expr = CreateRole(create_role = NullL)
    assertExpr(expr, "CreateRole", 1, Arity.Exact(1), Effect.Write)
  }

  test("Insert()") {
    val expr =
      Insert(insert = NullL, ts = NullL, action = NullL, params = Some(NullL))
    assertExpr(expr, "Insert", 4, Arity.Between(3, 4), Effect.Write)
  }

  test("Remove()") {
    val expr = Remove(remove = NullL, ts = NullL, action = NullL)
    assertExpr(expr, "Remove", 3, Arity.Exact(3), Effect.Write)
  }

  test("MoveDatabase()") {
    val expr = MoveDatabase(move_database = NullL, to = NullL)
    assertExpr(expr, "MoveDatabase", 2, Arity.Exact(2), Effect.Write)
  }

}
