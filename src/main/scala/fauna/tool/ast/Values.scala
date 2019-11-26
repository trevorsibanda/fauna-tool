package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

trait FaunaValue extends Expr

case class SetV(`@set`: Expr) extends FaunaValue {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    SetV(bf.buildChild(value, "@set"))
}

case class BytesV(`@bytes`: Expr) extends FaunaValue {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    BytesV(bf.buildChild(value, "@bytes"))
}

case class QueryV(`@query`: Expr) extends FaunaValue {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    QueryV(bf.buildChild(value, "@query"))
}

case class DateV(`@date`: Expr) extends FaunaValue {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    DateV(bf.buildChild(value, "@date"))
}

case class TimestampV(`@ts`: Expr) extends FaunaValue {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    TimestampV(bf.buildChild(value, "@ts"))
}
