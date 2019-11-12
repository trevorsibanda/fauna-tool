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

case class Merge(merge: Expr, `with`: Expr, lambda: Option[Expr]) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Merge(
      bf.buildChild(value, "merge"),
      bf.buildChild(value, "with"),
      bf.buildChildOpt(value, "lambda")
    )
}

case class Reduce(reduce: Expr, initial: Expr, collection: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Reduce(
      bf.buildChild(value, "reduce"),
      bf.buildChild(value, "initial"),
      bf.buildChild(value, "collection")
    )
}

case class ForEach(foreach: Expr, collection: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ForEach(bf.buildChild(value, "foreach"), bf.buildChild(value, "collection"))
}

case class Filter(filter: Expr, collection: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Filter(bf.buildChild(value, "filter"), bf.buildChild(value, "collection"))
}

case class MapFn(collection: Expr, map: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    MapFn(bf.buildChild(value, "collection"), bf.buildChild(value, "map"))

  override def name: String = "Map"
}

case class Join(join: Expr, `with`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Join(bf.buildChild(value, "join"), bf.buildChild(value, "with"))
}
