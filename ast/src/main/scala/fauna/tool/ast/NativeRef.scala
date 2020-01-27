package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

// Native classref constructors

case class Databases(databases: Expr) extends Expr {
  override val formKeys = Databases.formKeys
}

object Databases {
  val formKeys: List[Form.Key] = List("databases").map(Form.Key.Required(_))
  Form.add("Databases", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Databases(bf.buildChild(value, "databases"))

}

case class Indexes(indexes: Expr) extends Expr {
  override val formKeys = Indexes.formKeys
}

object Indexes {
  val formKeys: List[Form.Key] = List("indexes").map(Form.Key.Required(_))
  Form.add("Indexes", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Indexes(bf.buildChild(value, "indexes"))
}

case class Classes(classes: Expr) extends Expr {
  override val formKeys = Classes.formKeys
}

object Classes {
  val formKeys: List[Form.Key] = List("classes").map(Form.Key.Required(_))
  Form.add("Classes", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Classes(bf.buildChild(value, "classes"))
}

case class Collections(collections: Expr) extends Expr {
  override val formKeys = Collections.formKeys
}

object Collections {
  val formKeys: List[Form.Key] = List("collections").map(Form.Key.Required(_))
  Form.add("Collections", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Collections(bf.buildChild(value, "collections"))
}

case class Keys(keys: Expr) extends Expr {
  override val formKeys = Keys.formKeys
}

object Keys {
  val formKeys: List[Form.Key] = List("keys").map(Form.Key.Required(_))
  Form.add("Keys", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Keys(bf.buildChild(value, "keys"))
}

case class Tokens(tokens: Expr) extends Expr {
  override val formKeys = Tokens.formKeys
}

object Tokens {
  val formKeys: List[Form.Key] = List("tokens").map(Form.Key.Required(_))
  Form.add("Tokens", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Tokens(bf.buildChild(value, "tokens"))
}

case class Credentials(credentials: Expr) extends Expr {
  override val formKeys = Credentials.formKeys
}

object Credentials {
  val formKeys: List[Form.Key] = List("credentials").map(Form.Key.Required(_))
  Form.add("Credentials", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Credentials(bf.buildChild(value, "credentials"))
}

case class Functions(functions: Expr) extends Expr {
  override val formKeys = Functions.formKeys
}

object Functions {
  val formKeys: List[Form.Key] = List("functions").map(Form.Key.Required(_))
  Form.add("Functions", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Functions(bf.buildChild(value, "functions"))
}

case class Roles(roles: Expr) extends Expr {
  override val formKeys = Roles.formKeys
}

object Roles {
  val formKeys: List[Form.Key] = List("roles").map(Form.Key.Required(_))
  Form.add("Roles", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Roles(bf.buildChild(value, "roles"))
}
