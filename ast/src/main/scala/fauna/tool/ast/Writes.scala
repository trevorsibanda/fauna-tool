package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//writes

case class Create(
  create: Expr,
  params: Option[Expr]
) extends Expr {
  override val formKeys = Create.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object Create {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("create"), Form.Key.Optional("params"))
  Form.add("Create", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Create(bf.buildChild(value, "create"), bf.buildChildOpt(value, "params"))

}

case class Update(
  update: Expr,
  params: Option[Expr]
) extends Expr {
  override val formKeys = Update.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object Update {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("update"), Form.Key.Optional("params"))
  Form.add("Update", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Update(bf.buildChild(value, "update"), bf.buildChildOpt(value, "params"))

}

case class Replace(
  replace: Expr,
  params: Option[Expr]
) extends Expr {
  override val formKeys = Replace.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object Replace {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("replace"), Form.Key.Optional("params"))
  Form.add("Replace", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Replace(bf.buildChild(value, "replace"), bf.buildChildOpt(value, "params"))

}

case class Delete(delete: Expr) extends Expr {
  override val formKeys = Delete.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object Delete {
  val formKeys: List[Form.Key] = List("delete").map(Form.Key.Required(_))
  Form.add("Delete", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Delete(bf.buildChild(value, "delete"))

}

case class CreateClass(create_class: Expr) extends Expr {
  override val formKeys = CreateClass.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object CreateClass {
  val formKeys: List[Form.Key] = List("create_class").map(Form.Key.Required(_))
  Form.add("CreateClass", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    CreateClass(bf.buildChild(value, "create_class"))

}

case class CreateCollection(create_collection: Expr) extends Expr {
  override val formKeys = CreateCollection.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object CreateCollection {
  val formKeys: List[Form.Key] = List("create_collection").map(Form.Key.Required(_))
  Form.add("CreateCollection", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    CreateCollection(bf.buildChild(value, "create_collection"))

}

case class CreateDatabase(create_database: Expr) extends Expr {
  override val formKeys = CreateDatabase.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object CreateDatabase {
  val formKeys: List[Form.Key] = List("create_database").map(Form.Key.Required(_))
  Form.add("CreateDatabase", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    CreateDatabase(bf.buildChild(value, "create_database"))

}

case class CreateIndex(create_index: Expr) extends Expr {
  override val formKeys = CreateIndex.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object CreateIndex {
  val formKeys: List[Form.Key] = List("create_index").map(Form.Key.Required(_))
  Form.add("CreateIndex", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    CreateIndex(bf.buildChild(value, "create_index"))

}

case class CreateKey(create_key: Expr) extends Expr {
  override val formKeys = CreateKey.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object CreateKey {
  val formKeys: List[Form.Key] = List("create_key").map(Form.Key.Required(_))
  Form.add("CreateKey", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr = {
    CreateKey(bf.buildChild(value, "create_key"))
  }

}

case class CreateFunction(create_function: Expr) extends Expr {
  override val formKeys = CreateFunction.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object CreateFunction {
  val formKeys: List[Form.Key] = List("create_function").map(Form.Key.Required(_))
  Form.add("CreateFunction", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    CreateFunction(bf.buildChild(value, "create_function"))

}

case class CreateRole(create_role: Expr) extends Expr {
  override val formKeys = CreateRole.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object CreateRole {
  val formKeys: List[Form.Key] = List("create_role").map(Form.Key.Required(_))
  Form.add("CreateRole", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    CreateRole(bf.buildChild(value, "create_role"))

}

case class Insert(
  insert: Expr,
  ts: Expr,
  action: Expr,
  params: Option[Expr]
) extends Expr {
  override val formKeys = Insert.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object Insert {

  val formKeys
    : List[Form.Key] = List("insert", "ts", "action").map(Form.Key.req) ++ List(
    Form.Key.opt("params")
  )
  Form.add("Insert", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Insert(
      bf.buildChild(value, "insert"),
      bf.buildChild(value, "ts"),
      bf.buildChild(value, "action"),
      bf.buildChildOpt(value, "params")
    )

}

case class Remove(
  remove: Expr,
  ts: Expr,
  action: Expr
) extends Expr {
  override val formKeys = Remove.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object Remove {

  val formKeys: List[Form.Key] =
    List("remove", "ts", "action").map(Form.Key.Required(_))
  Form.add("Remove", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Remove(
      bf.buildChild(value, "remove"),
      bf.buildChild(value, "ts"),
      bf.buildChild(value, "action")
    )

}

case class MoveDatabase(
  move_database: Expr,
  to: Expr
) extends Expr {
  override val formKeys = MoveDatabase.formKeys

  override def effect: Effect = effect(Effect.Write)
}

object MoveDatabase {

  val formKeys: List[Form.Key] =
    List("move_database", "to").map(Form.Key.Required(_))
  Form.add("MoveDatabase", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    MoveDatabase(bf.buildChild(value, "move_database"), bf.buildChild(value, "to"))

}
