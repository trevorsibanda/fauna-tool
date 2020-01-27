package fauna.tool.ast

import scala.scalajs.js.annotation._

abstract class FaunaValue(key: String) extends Expr

case class SetV(`@set`: Expr) extends FaunaValue("@set") {
  override val formKeys = SetV.formKeys
}

object SetV {
  val formKeys: List[Form.Key] = List(Form.Key.req("@set"))
  Form.add("SetV", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    SetV(bf.buildChild(value, "@set"))
}

case class BytesV(`@bytes`: Expr) extends FaunaValue("@bytes") {
  override val formKeys = BytesV.formKeys
}

object BytesV {
  val formKeys: List[Form.Key] = List(Form.Key.req("@bytes"))
  Form.add("BytesV", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    BytesV(bf.buildChild(value, "@bytes"))
}

case class QueryV(`@query`: Expr) extends FaunaValue("@query") {
  override val formKeys = QueryV.formKeys
}

object QueryV {
  val formKeys: List[Form.Key] = List(Form.Key.req("@query"))
  Form.add("QueryV", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    QueryV(bf.buildChild(value, "@query"))
}

case class DateV(`@date`: Expr) extends FaunaValue("@date") {
  override val formKeys = DateV.formKeys
}

object DateV {
  val formKeys: List[Form.Key] = List(Form.Key.req("@date"))
  Form.add("DateV", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    DateV(bf.buildChild(value, "@date"))
}

case class TimestampV(`@ts`: Expr) extends FaunaValue("@ts") {
  override val formKeys = TimestampV.formKeys
}

object TimestampV {
  val formKeys: List[Form.Key] = List(Form.Key.req("@ts"))
  Form.add("TimestampV", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    TimestampV(bf.buildChild(value, "@ts"))
}
