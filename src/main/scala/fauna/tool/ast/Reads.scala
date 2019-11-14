package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

//reads
case class Exists(exists: Expr, ts: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Exists(bf.buildChild(value, "exists"), bf.buildChildOpt(value, "ts"))

  override def effect: Effect = effect(Effect.Read)
}

case class Get(get: Expr, ts: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Get(bf.buildChild(value, "get"), bf.buildChildOpt(value, "ts"))

  override def effect: Effect = effect(Effect.Read)
}

case class KeyFromSecret(key_from_secret: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    KeyFromSecret(bf.buildChild(value, "key_from_secret"))

  override def effect: Effect = effect(Effect.Read)
}

case class Paginate(
  val paginate: Expr,
  val before: Option[Expr],
  val after: Option[Expr],
  val cursor: Option[Expr],
  val ts: Option[Expr],
  val size: Option[Expr],
  val events: Option[Expr],
  val sources: Option[Expr]
) extends FnExpr {
  override def name = "Paginate"

  override def effect: Effect = effect(Effect.Read)

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = Paginate(
    bf.buildChild(value, "paginate"),
    bf.buildChildOpt(value, "before"),
    bf.buildChildOpt(value, "after"),
    bf.buildChildOpt(value, "cursor"),
    bf.buildChildOpt(value, "ts"),
    bf.buildChildOpt(value, "size"),
    bf.buildChildOpt(value, "events"),
    bf.buildChildOpt(value, "sources")
  )
}

case class Select(select: Expr, from: Expr, default: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Select(
      bf.buildChild(value, "select"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )

  override def effect: Effect = effect(Effect.Read)
}

case class SelectAll(select_all: Expr, from: Expr, default: Option[Expr])
    extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    SelectAll(
      bf.buildChild(value, "select_all"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )

  override def effect: Effect = effect(Effect.Read)
}

case class SelectAsIndex(select_as_index: Expr, from: Expr, default: Option[Expr])
    extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    SelectAsIndex(
      bf.buildChild(value, "select_as_index"),
      bf.buildChild(value, "from"),
      bf.buildChildOpt(value, "default")
    )

  override def effect: Effect = effect(Effect.Read)
}
