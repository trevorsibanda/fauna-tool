package fauna.tool.ast

import fauna.tool.parser.ASTBuilder


// Native classref constructors
case class Databases(databases: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Databases(bf.buildChild(value, "databases"))

}

case class Indexes(indexes: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Indexes(bf.buildChild(value, "indexes"))
}

case class Classes(classes: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Classes(bf.buildChild(value, "classes"))
}

case class Collections(collections: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Collections(bf.buildChild(value, "collections"))
}

case class Keys(keys: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Keys(bf.buildChild(value, "keys"))
}

case class Tokens(tokens: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Tokens(bf.buildChild(value, "tokens"))
}

case class Credentials(credentials: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Credentials(bf.buildChild(value, "credentials"))
}

case class Functions(functions: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Functions(bf.buildChild(value, "functions"))
}

case class Roles(roles: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Roles(bf.buildChild(value, "roles"))
}
