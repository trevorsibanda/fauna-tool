package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

// Set ref constructors
case class Singleton(singleton: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Singleton(bf.buildChild(value, "singleton"))
}

case class Events(events: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Events(bf.buildChild(value, "events"))
}



case class Match(
  `match`: Expr,
  index: Option[Expr],
  terms: Option[Expr]
) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Match(
      bf.buildChild(value, "match"),
      bf.buildChildOpt(value, "index"),
      bf.buildChildOpt(value, "terms")
    )
}

case class Union(union: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Union(bf.buildChild(value, "union"))
}

case class Intersection(intersection: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Intersection(bf.buildChild(value, "intersection"))
}

case class Difference(difference: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Difference(bf.buildChild(value, "difference"))
}

case class Distinct(distinct: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Distinct(bf.buildChild(value, "distinct"))
}

case class Range(range: Expr, from: Expr, to: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Range(
      bf.buildChild(value, "range"),
      bf.buildChild(value, "from"),
      bf.buildChild(value, "to")
    )
}
