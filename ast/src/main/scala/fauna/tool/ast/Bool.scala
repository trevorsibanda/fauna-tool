package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//bool

case class And(and: Expr) extends Expr {
  override val formKeys = And.formKeys
}

object And {
  val formKeys: List[Form.Key] = List("and").map(Form.Key.Required(_))
  Form.add("And", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    And(bf.buildChild(value, "and"))
}

case class Or(or: Expr) extends Expr {
  override val formKeys = Or.formKeys
}

object Or {
  val formKeys: List[Form.Key] = List("or").map(Form.Key.Required(_))
  Form.add("Or", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Or(bf.buildChild(value, "or"))
}

case class Not(not: Expr) extends Expr {
  override val formKeys = Not.formKeys
}

object Not {
  val formKeys: List[Form.Key] = List("not").map(Form.Key.Required(_))
  Form.add("Not", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Not(bf.buildChild(value, "not"))
}
