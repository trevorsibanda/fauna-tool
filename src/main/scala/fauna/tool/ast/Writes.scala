package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

//writes
case class Create(create: Expr, params: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Create(bf.buildChild(value, "create"), bf.buildChildOpt(value, "params"))

  override def effect: Effect = effect(Effect.Write)
}

case class Update(update: Expr, params: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Update(bf.buildChild(value, "update"), bf.buildChildOpt(value, "params"))

  override def effect: Effect = effect(Effect.Write)
}

case class Replace(replace: Expr, params: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Replace(bf.buildChild(value, "replace"), bf.buildChildOpt(value, "params"))

  override def effect: Effect = effect(Effect.Write)
}

case class Delete(delete: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Delete(bf.buildChild(value, "delete"))

  override def effect: Effect = effect(Effect.Write)
}

case class CreateClass(create_class: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    CreateClass(bf.buildChild(value, "create_class"))

  override def effect: Effect = effect(Effect.Write)

}

case class CreateCollection(create_collection: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    CreateCollection(bf.buildChild(value, "create_collection"))

  override def effect: Effect = effect(Effect.Write)
}

case class CreateDatabase(create_database: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    CreateDatabase(bf.buildChild(value, "create_database"))

  override def effect: Effect = effect(Effect.Write)
}

case class CreateIndex(create_index: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    CreateIndex(bf.buildChild(value, "create_index"))

  override def effect: Effect = effect(Effect.Write)
}

case class CreateKey(create_key: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = {
    CreateKey(bf.buildChild(value, "create_key"))
  }

  override def effect: Effect = effect(Effect.Write)
}

case class CreateFunction(create_function: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    CreateFunction(bf.buildChild(value, "create_function"))

  override def effect: Effect = effect(Effect.Write)
}

case class CreateRole(create_role: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    CreateRole(bf.buildChild(value, "create_role"))

  override def effect: Effect = effect(Effect.Write)
}

case class Insert(insert: Expr, ts: Expr, action: Expr, params: Option[Expr])
    extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Insert(
      bf.buildChild(value, "insert"),
      bf.buildChild(value, "ts"),
      bf.buildChild(value, "action"),
      bf.buildChildOpt(value, "params")
    )

  override def effect: Effect = effect(Effect.Write)
}

case class Remove(remove: Expr, ts: Expr, action: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Remove(
      bf.buildChild(value, "remove"),
      bf.buildChild(value, "ts"),
      bf.buildChild(value, "action")
    )

  override def effect: Effect = effect(Effect.Write)
}

case class MoveDatabase(move_database: Expr, to: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    MoveDatabase(bf.buildChild(value, "move_database"), bf.buildChild(value, "to"))

  override def effect: Effect = effect(Effect.Write)
}
