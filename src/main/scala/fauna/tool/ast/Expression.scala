package fauna.tool.ast

import fauna.tool.parser.ASTBuilder


trait FnExpr extends Expr

case class Array(l: List[Expr]) extends Literal {
  override def toString = l.mkString("[", ",", "]")

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    bf.build(value) //fixme
}

case class UnknownExpression[T](value: T) extends Expr {
  override def children: Seq[Option[Expr]] = Seq()
}

case object Undefined extends Expr {
  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = ???

  override def children: Seq[Option[Expr]] = Seq()
}

case class Body(expr: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Body(bf.buildChild(value, "expr"))
}

case class Lambda(lambda: Expr, expr: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Lambda(bf.buildChild(value, "lambda"), bf.buildChild(value, "expr"))
}

case class Call(call: Expr, arguments: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Call(bf.buildChild(value, "call"), bf.buildChildOpt(value, "arguments"))
}

case class Join(join: Expr, `with`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Join(bf.buildChild(value, "join"), bf.buildChild(value, "with"))
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
}

case class Do(`do`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Do(bf.buildChild(value, "do"))
}

case class If(`if`: Expr, then: Expr, `else`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    If(
      bf.buildChild(value, "if"),
      bf.buildChild(value, "then"),
      bf.buildChild(value, "else")
    )
}

case class Let(let: Expr, in: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Let(bf.buildChild(value, "let"), bf.buildChild(value, "in"))
}

case class Var(`var`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Var(bf.buildChild(value, "var"))
}

case class At(at: Expr, expr: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    At(bf.buildChild(value, "at"), bf.buildChild(value, "expr"))
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

case class Abort(abort: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Abort(bf.buildChild(value, "abort"))
}

case class Equals(equals: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Equals(bf.buildChild(value, "equals"))
}

case class Contains(contains: Expr, in: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Contains(bf.buildChild(value, "contains"), bf.buildChild(value, "in"))
}

case class Select(select: Expr, from: Expr, default: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Select(
      bf.buildChild(value, "select"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )
}

case class SelectAll(select_all: Expr, from: Expr, default: Option[Expr])
    extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    SelectAll(
      bf.buildChild(value, "select_all"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )
}

case class SelectAsIndex(select_as_index: Expr, from: Expr, default: Option[Expr])
    extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    SelectAsIndex(
      bf.buildChild(value, "select_as_index"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )
}
