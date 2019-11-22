package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

case class Ref(`@ref`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = {
    val ref = bf.readChild(
      bf.readChild(bf.readChild(bf.readChild(value, "@ref"), "class"), "@ref"),
      "id"
    )
    bf.buildChild(value, "@ref") match {
      case ObjectL(map) if map.isDefinedAt("class") =>
        Ref2(strRefToExpr(bf.build(ref)), map.get("id"), map.get("scope"))
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

case class Ref2(ref: Expr, id: Option[Expr], scope: Option[Expr]) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = Ref2(
    bf.buildChild(value, "ref"),
    bf.buildChildOpt(value, "id"),
    bf.buildChildOpt(value, "scope")
  )

  override def name: String = "Ref"

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
    NewID(bf.buildChild(value, "new_id"))
}
