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

case class ToInteger(to_integer: Expr) extends Expr {
  override val formKeys = ToInteger.formKeys
}

object ToInteger {
  val formKeys: List[Form.Key] = List("to_integer").map(Form.Key.Required(_))
  Form.add("ToInteger", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToInteger(bf.buildChild(value, "to_integer"))
}

case class ToDouble(to_double: Expr) extends Expr {
  override val formKeys = ToDouble.formKeys
}

object ToDouble {
  val formKeys: List[Form.Key] = List("to_double").map(Form.Key.Required(_))
  Form.add("ToDouble", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ToDouble(bf.buildChild(value, "to_double"))
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
