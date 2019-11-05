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

abstract class AbstractPaginate(
  paginate: Expr,
  before: Option[Expr],
  after: Option[Expr],
  cursor: Option[Expr],
  ts: Option[Expr],
  size: Option[Expr],
  events: Option[Expr],
  sources: Option[Expr]
) extends FnExpr {
  override def name = "Paginate"

  override def effect: Effect = effect(Effect.Read)
}

case class PaginateCursor(
  paginate: Expr,
  cursor: Expr,
  ts: Option[Expr],
  size: Option[Expr],
  events: Option[Expr],
  sources: Option[Expr]
) extends AbstractPaginate(
      paginate,
      None,
      None,
      Some(cursor),
      ts,
      size,
      events,
      sources
    ) {

  override def name = "Paginate"

  override def effect: Effect = effect(Effect.Read)

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = PaginateCursor(
    bf.buildChild(value, "paginate"),
    bf.buildChild(value, "cursor"),
    bf.buildChildOpt(value, "ts"),
    bf.buildChildOpt(value, "size"),
    bf.buildChildOpt(value, "events"),
    bf.buildChildOpt(value, "sources")
  )
}

case class PaginateBefore(
  paginate: Expr,
  before: Expr,
  ts: Option[Expr],
  size: Option[Expr],
  events: Option[Expr],
  sources: Option[Expr]
) extends AbstractPaginate(
      paginate,
      Some(before),
      None,
      None,
      ts,
      size,
      events,
      sources
    ) {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = PaginateBefore(
    bf.buildChild(value, "paginate"),
    bf.buildChild(value, "before"),
    bf.buildChildOpt(value, "ts"),
    bf.buildChildOpt(value, "size"),
    bf.buildChildOpt(value, "events"),
    bf.buildChildOpt(value, "sources")
  )

  override def effect: Effect = effect(Effect.Read)
}

case class PaginateAfter(
  paginate: Expr,
  after: Option[Expr],
  ts: Option[Expr],
  size: Option[Expr],
  events: Option[Expr],
  sources: Option[Expr]
) extends AbstractPaginate(paginate, None, after, None, ts, size, events, sources) {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = PaginateAfter(
    bf.buildChild(value, "paginate"),
    bf.buildChildOpt(value, "after"),
    bf.buildChildOpt(value, "ts"),
    bf.buildChildOpt(value, "size"),
    bf.buildChildOpt(value, "events"),
    bf.buildChildOpt(value, "sources")
  )

}
