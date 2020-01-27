package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//conversion

case class ToString(to_string: Expr) extends Expr {
  override val formKeys = ToString.formKeys
}

object ToString {
  val formKeys: List[Form.Key] = List("to_string").map(Form.Key.Required(_))
  Form.add("ToString", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToString(bf.buildChild(value, "to_string"))
}

case class ToNumber(to_number: Expr) extends Expr {
  override val formKeys = ToNumber.formKeys
}

object ToNumber {
  val formKeys: List[Form.Key] = List("to_number").map(Form.Key.Required(_))
  Form.add("ToNumber", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToNumber(bf.buildChild(value, "to_number"))
}

case class ToTime(to_time: Expr) extends Expr {
  override val formKeys = ToTime.formKeys
}

object ToTime {
  val formKeys: List[Form.Key] = List("to_time").map(Form.Key.Required(_))
  Form.add("ToTime", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToTime(bf.buildChild(value, "to_time"))
}

case class ToDate(to_date: Expr) extends Expr {
  override val formKeys = ToDate.formKeys
}

object ToDate {
  val formKeys: List[Form.Key] = List("to_date").map(Form.Key.Required(_))
  Form.add("ToDate", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToDate(bf.buildChild(value, "to_date"))
}

case class ToObject(to_object: Expr) extends Expr {
  override val formKeys = ToObject.formKeys
}

object ToObject {
  val formKeys: List[Form.Key] = List("to_object").map(Form.Key.Required(_))
  Form.add("ToObject", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToObject(bf.buildChild(value, "to_object"))
}

case class ToArray(to_array: Expr) extends Expr {
  override val formKeys = ToArray.formKeys
}

object ToArray {
  val formKeys: List[Form.Key] = List("to_array").map(Form.Key.Required(_))
  Form.add("ToArray", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToArray(bf.buildChild(value, "to_array"))
}
