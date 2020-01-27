package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

// Set ref constructors

case class Singleton(singleton: Expr) extends Expr {
  override val formKeys = Singleton.formKeys
}

object Singleton {
  val formKeys: List[Form.Key] = List("singleton").map(Form.Key.Required(_))
  Form.add("Singleton", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Singleton(bf.buildChild(value, "singleton"))
}

case class Events(events: Expr) extends Expr {
  override val formKeys = Events.formKeys
}

object Events {
  val formKeys: List[Form.Key] = List("events").map(Form.Key.Required(_))
  Form.add("Events", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Events(bf.buildChild(value, "events"))
}

case class Match(
  `match`: Expr,
  terms: Option[Expr]
) extends Expr {
  override val formKeys = Match.formKeys
}

object Match {

  val formKeys: List[Form.Key] = List(Form.Key.req("match"), Form.Key.opt("terms"))
  Form.add("Match", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Match(
      bf.buildChild(value, "match"),
      bf.buildChildOpt(value, "terms")
    )
}

case class Union(union: Expr) extends Expr {
  override val formKeys = Union.formKeys
}

object Union {
  val formKeys: List[Form.Key] = List("union").map(Form.Key.Required(_))
  Form.add("Union", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Union(bf.buildChild(value, "union"))
}

case class Intersection(intersection: Expr) extends Expr {
  override val formKeys = Intersection.formKeys
}

object Intersection {
  val formKeys: List[Form.Key] = List("intersection").map(Form.Key.Required(_))
  Form.add("Intersection", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Intersection(bf.buildChild(value, "intersection"))
}

case class Difference(difference: Expr) extends Expr {
  override val formKeys = Difference.formKeys
}

object Difference {
  val formKeys: List[Form.Key] = List("difference").map(Form.Key.Required(_))
  Form.add("Difference", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Difference(bf.buildChild(value, "difference"))
}

case class Distinct(distinct: Expr) extends Expr {
  override val formKeys = Distinct.formKeys
}

object Distinct {
  val formKeys: List[Form.Key] = List("distinct").map(Form.Key.Required(_))
  Form.add("Distinct", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Distinct(bf.buildChild(value, "distinct"))
}

case class Range(
  range: Expr,
  from: Expr,
  to: Expr
) extends Expr {
  override val formKeys = Range.formKeys
}

object Range {

  val formKeys: List[Form.Key] =
    List("range", "from", "to").map(Form.Key.Required(_))
  Form.add("Range", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Range(
      bf.buildChild(value, "range"),
      bf.buildChild(value, "from"),
      bf.buildChild(value, "to")
    )
}
