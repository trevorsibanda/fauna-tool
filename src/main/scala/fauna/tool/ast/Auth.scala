package fauna.tool.ast
import org.json4s.JsonAST.JValue

import fauna.tool.parser.ASTBuilder

//auth
case class Identity(identity: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Identity(bf.buildChild(value, "identity"))
}

case class HasIdentity(has_identity: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    HasIdentity(bf.buildChild(value, "has_identity"))
}

case class Identify(identify: Expr, password: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Identify(bf.buildChild(value, "identify"), bf.buildChild(value, "password"))
}

case class Login(login: Expr, params: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Login(bf.buildChild(value, "login"), bf.buildChild(value, "params"))
}

case class Logout(logout: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Logout(bf.buildChild(value, "logout"))
}
