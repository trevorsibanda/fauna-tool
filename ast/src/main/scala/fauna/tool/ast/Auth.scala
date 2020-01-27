package fauna.tool.ast

//auth

case class Identity(identity: Expr) extends Expr {
  override val formKeys = Identity.formKeys
}

object Identity {
  val formKeys: List[Form.Key] = List("identity").map(Form.Key.Required(_))
  Form.add("Identity", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Identity(bf.buildChild(value, "identity"))
}

case class HasIdentity(has_identity: Expr) extends Expr {
  override val formKeys = HasIdentity.formKeys
}

object HasIdentity {
  val formKeys: List[Form.Key] = List("has_identity").map(Form.Key.Required(_))
  Form.add("HasIdentity", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    HasIdentity(bf.buildChild(value, "has_identity"))
}

case class Identify(
  identify: Expr,
  password: Expr
) extends Expr {
  override val formKeys = Identify.formKeys
}

object Identify {

  val formKeys: List[Form.Key] =
    List("identify", "password").map(Form.Key.Required(_))
  Form.add("Identify", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Identify(bf.buildChild(value, "identify"), bf.buildChild(value, "password"))
}

case class Login(login: Expr, params: Expr) extends Expr {
  override val formKeys = Login.formKeys
}

object Login {
  val formKeys: List[Form.Key] = List("login", "params").map(Form.Key.Required(_))
  Form.add("Login", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Login(bf.buildChild(value, "login"), bf.buildChild(value, "params"))
}

case class Logout(logout: Expr) extends Expr {
  override val formKeys = Logout.formKeys
}

object Logout {
  val formKeys: List[Form.Key] = List("logout").map(Form.Key.Required(_))
  Form.add("Logout", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Logout(bf.buildChild(value, "logout"))
}
