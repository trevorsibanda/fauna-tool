package fauna.tool.ast

import fauna.tool.ast.Expr
import fauna.tool.parser.ASTBuilder

//collection
case class Prepend(prepend: Expr, collection: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Prepend(bf.buildChild(value, "prepend"), bf.buildChild(value, "collection"))
}

case class Append(append: Expr, collection: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Append(bf.buildChild(value, "append"), bf.buildChild(value, "collection"))
}

case class Take(take: Expr, collection: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Take(bf.buildChild(value, "take"), bf.buildChild(value, "collection"))
}

case class Drop(drop: Expr, collection: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Drop(bf.buildChild(value, "drop"), bf.buildChild(value, "collection"))
}

case class IsEmpty(is_empty: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    IsEmpty(bf.buildChild(value, "is_empty"))
}

case class IsNonEmpty(is_nonempty: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    IsNonEmpty(bf.buildChild(value, "is_nonempty"))
}
