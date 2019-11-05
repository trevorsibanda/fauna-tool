package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

case class Ref(`@ref`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    bf.buildChild(value, "@ref") match {
      case ObjectL(map) if map.isDefinedAt("id") =>
        map("id") match {
          case l: StringL => strRefToExpr(value, l)
          case _          => Ref(ObjectL(map))
        }
      case s: StringL => strRefToExpr(value, s)
      case expr       => Ref(expr)
    }

  private def strRefToExpr[T](value: T, strL: StringL)(
    implicit bf: ASTBuilder[T]
  ): Expr = strL match {
    case StringL("databases")   => Databases(NullL)
    case StringL("indexes")     => Indexes(NullL)
    case StringL("collections") => Collections(NullL)
    case id                     => Ref2(NullL, Some(id), bf.buildChildOpt(value, "scope"))
  }
}

case class Ref2(ref: Expr, id: Option[Expr], scope: Option[Expr]) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = Ref2(
    bf.buildChild(value, "ref"),
    bf.buildChildOpt(value, "id"),
    bf.buildChildOpt(value, "scope")
  )

}

case class Database(database: Expr, scope: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Database(bf.buildChild(value, "database"), bf.buildChildOpt(value, "scope"))
}

case class Index(index: Expr, scope: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Index(bf.buildChild(value, "index"), bf.buildChildOpt(value, "scope"))
}

case class Class(`class`: Expr, scope: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Class(bf.buildChild(value, "class"), bf.buildChildOpt(value, "scope"))
}

case class Collection(collection: Expr, scope: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Collection(bf.buildChild(value, "collection"), bf.buildChildOpt(value, "scope"))
}

case class Function(function: Expr, scope: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Function(bf.buildChild(value, "function"), bf.buildChildOpt(value, "scope"))
}

case class Role(role: Expr, scope: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Role(bf.buildChild(value, "role"), bf.buildChildOpt(value, "scope"))
}

//deprecated, use new_id
case class NextID(next_id: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    NextID(bf.buildChild(value, "next_id"))
}

case class NewID(new_id: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    NextID(bf.buildChild(value, "new_id"))
}
