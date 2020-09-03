package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//Comparison

case class LT(lt: Expr) extends Expr {
  override val formKeys = LT.formKeys
}

object LT {
  val formKeys: List[Form.Key] = List("lt").map(Form.Key.Required(_))
  Form.add("LT", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    LT(bf.buildChild(value, "lt"))
}

case class LTE(lte: Expr) extends Expr {
  override val formKeys = LTE.formKeys
}

object LTE {
  val formKeys: List[Form.Key] = List("lte").map(Form.Key.Required(_))
  Form.add("LTE", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    LTE(bf.buildChild(value, "lte"))
}

case class GT(gt: Expr) extends Expr {
  override val formKeys = GT.formKeys

  override def arity: Arity = Arity.VarArgs
}

object GT {
  val formKeys: List[Form.Key] = List("gt").map(Form.Key.Required(_))
  Form.add("GT", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    GT(bf.buildChild(value, "gt"))

}

case class GTE(gte: Expr) extends Expr {
  override val formKeys = GTE.formKeys

  override def arity: Arity = Arity.VarArgs
}

object GTE {
  val formKeys: List[Form.Key] = List("gte").map(Form.Key.Required(_))
  Form.add("GTE", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    GTE(bf.buildChild(value, "gte"))

}

case class Equals(equals: Expr) extends Expr {
  override val formKeys = Equals.formKeys
}

object Equals {
  val formKeys: List[Form.Key] = List("equals").map(Form.Key.Required(_))
  Form.add("Equals", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Equals(bf.buildChild(value, "equals"))
}

case class Contains(contains: Expr, in: Expr) extends Expr {
  override val formKeys = Contains.formKeys
}

object Contains {
  val formKeys: List[Form.Key] = List("contains", "in").map(Form.Key.Required(_))
  Form.add("Contains", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Contains(bf.buildChild(value, "contains"), bf.buildChild(value, "in"))
}

case class ContainsPath(contains_path: Expr, in: Expr) extends Expr {
  override val formKeys = ContainsPath.formKeys
}

object ContainsPath {

  val formKeys: List[Form.Key] =
    List("contains_path", "in").map(Form.Key.Required(_))
  Form.add("ContainsPath", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ContainsPath(bf.buildChild(value, "contains_path"), bf.buildChild(value, "in"))
}

case class ContainsValue(contains_value: Expr, in: Expr) extends Expr {
  override val formKeys = ContainsValue.formKeys
}

object ContainsValue {

  val formKeys: List[Form.Key] =
    List("contains_value", "in").map(Form.Key.Required(_))
  Form.add("ContainsValue", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ContainsValue(
      bf.buildChild(value, "contains_value"),
      bf.buildChild(value, "in")
    )
}

case class ContainsField(contains_field: Expr, in: Expr) extends Expr {
  override val formKeys = ContainsField.formKeys
}

object ContainsField {

  val formKeys: List[Form.Key] =
    List("contains_field", "in").map(Form.Key.Required(_))
  Form.add("ContainsField", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ContainsField(
      bf.buildChild(value, "contains_field"),
      bf.buildChild(value, "in")
    )
}
