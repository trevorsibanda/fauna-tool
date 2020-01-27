package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

case class Ref(`@ref`: Expr) extends Expr {
  override val formKeys = Ref.formKeys
}

object Ref {
  val formKeys: List[Form.Key] = List("@ref").map(Form.Key.Required(_))
  Form.add("Ref", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr = {
    bf.buildChild(value, "@ref") match {
      case ObjectL(map) if map.isDefinedAt("id") =>
        strRefToExpr(map("id"))
      case s: StringL => strRefToExpr(s)
      case expr       => Ref(expr)
    }
  }

  private def strRefToExpr[T](e: Expr): Expr = e match {
    case StringL("databases")                         => Databases(NullL)
    case StringL("indexes")                           => Indexes(NullL)
    case StringL("tokens")                            => Tokens(NullL)
    case StringL("functions")                         => Functions(NullL)
    case StringL("classes")                           => Classes(NullL)
    case StringL("collections")                       => Collections(NullL)
    case StringL(s) if """^(\w+)/(.*)""".r.matches(s) => Ref(StringL(s))
    case _                                            => e
  }
}

case class Ref2(
  ref: Expr,
  id: Option[Expr],
  scope: Option[Expr]
) extends Expr {
  override val formKeys = Ref2.formKeys

  override def name: String = "Ref"
}

object Ref2 {

  val formKeys: List[Form.Key] = List("ref").map(Form.Key.Required(_)) ++ List(
    "id",
    "scope"
  ).map(Form.Key.Optional(_))
  Form.add("Ref", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr = Ref2(
    bf.buildChild(value, "ref"),
    bf.buildChildOpt(value, "id"),
    bf.buildChildOpt(value, "scope")
  )

}

case class Database(
  database: Expr,
  scope: Option[Expr]
) extends Expr {
  override val formKeys = Database.formKeys
}

object Database {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("database"), Form.Key.Optional("scope"))
  Form.add("Database", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Database(bf.buildChild(value, "database"), bf.buildChildOpt(value, "scope"))
}

case class Index(
  index: Expr,
  scope: Option[Expr]
) extends Expr {
  override val formKeys = Index.formKeys
}

object Index {
  val formKeys: List[Form.Key] = List(Form.Key.req("index"), Form.Key.opt("scope"))
  Form.add("Index", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Index(bf.buildChild(value, "index"), bf.buildChildOpt(value, "scope"))
}

case class Class(
  `class`: Expr,
  scope: Option[Expr]
) extends Expr {
  override val formKeys = Class.formKeys
}

object Class {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("class"), Form.Key.Optional("scope"))
  Form.add("Class", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Class(bf.buildChild(value, "class"), bf.buildChildOpt(value, "scope"))
}

case class Collection(
  collection: Expr,
  scope: Option[Expr]
) extends Expr {
  override val formKeys = Collection.formKeys
}

object Collection {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("collection"), Form.Key.Optional("scope"))
  Form.add("Collection", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Collection(bf.buildChild(value, "collection"), bf.buildChildOpt(value, "scope"))
}

case class Function(
  function: Expr,
  scope: Option[Expr]
) extends Expr {
  override val formKeys = Function.formKeys
}

object Function {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("function"), Form.Key.Optional("scope"))
  Form.add("Function", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Function(bf.buildChild(value, "function"), bf.buildChildOpt(value, "scope"))
}

case class Role(
  role: Expr,
  scope: Option[Expr]
) extends Expr {
  override val formKeys = Role.formKeys
}

object Role {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("role"), Form.Key.Optional("scope"))
  Form.add("Role", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Role(bf.buildChild(value, "role"), bf.buildChildOpt(value, "scope"))
}

//deprecated, use new_id

case class NextID(next_id: Expr) extends Expr {
  override val formKeys = NextID.formKeys
}

object NextID {
  val formKeys: List[Form.Key] = List("next_id").map(Form.Key.Required(_))
  Form.add("NextID", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    NextID(bf.buildChild(value, "next_id"))
}

case class NewID(new_id: Expr) extends Expr {
  override val formKeys = NewID.formKeys
}

object NewID {
  val formKeys: List[Form.Key] = List("new_id").map(Form.Key.Required(_))
  Form.add("NewID", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    NewID(bf.buildChild(value, "new_id"))
}
