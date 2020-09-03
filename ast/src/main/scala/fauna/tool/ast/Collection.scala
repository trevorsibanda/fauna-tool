package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//collection

case class Prepend(
  prepend: Expr,
  collection: Expr
) extends Expr {
  override val formKeys = Prepend.formKeys
}

object Prepend {

  val formKeys: List[Form.Key] =
    List("prepend", "collection").map(Form.Key.Required(_))
  Form.add("Prepend", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Prepend(bf.buildChild(value, "prepend"), bf.buildChild(value, "collection"))
}

case class Append(
  append: Expr,
  collection: Expr
) extends Expr {
  override val formKeys = Append.formKeys
}

object Append {

  val formKeys: List[Form.Key] =
    List("append", "collection").map(Form.Key.Required(_))
  Form.add("Append", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Append(bf.buildChild(value, "append"), bf.buildChild(value, "collection"))
}

case class Take(take: Expr, collection: Expr) extends Expr {
  override val formKeys = Take.formKeys
}

object Take {
  val formKeys: List[Form.Key] = List("take", "collection").map(Form.Key.Required(_))
  Form.add("Take", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Take(bf.buildChild(value, "take"), bf.buildChild(value, "collection"))
}

case class Drop(drop: Expr, collection: Expr) extends Expr {
  override val formKeys = Drop.formKeys
}

object Drop {
  val formKeys: List[Form.Key] = List("drop", "collection").map(Form.Key.Required(_))
  Form.add("Drop", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Drop(bf.buildChild(value, "drop"), bf.buildChild(value, "collection"))
}

case class IsEmpty(is_empty: Expr) extends Expr {
  override val formKeys = IsEmpty.formKeys
}

object IsEmpty {
  val formKeys: List[Form.Key] = List("is_empty").map(Form.Key.Required(_))
  Form.add("IsEmpty", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    IsEmpty(bf.buildChild(value, "is_empty"))
}

case class IsNonEmpty(is_nonempty: Expr) extends Expr {
  override val formKeys = IsNonEmpty.formKeys
}

object IsNonEmpty {
  val formKeys: List[Form.Key] = List("is_nonempty").map(Form.Key.Required(_))
  Form.add("IsNonEmpty", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    IsNonEmpty(bf.buildChild(value, "is_nonempty"))
}

case class Merge(
  merge: Expr,
  `with`: Expr,
  lambda: Option[Expr]
) extends Expr {
  override val formKeys = Merge.formKeys
}

object Merge {

  val formKeys
    : List[Form.Key] = List("merge", "with").map(Form.Key.Required(_)) ++ List(
    Form.Key.opt("lambda")
  )
  Form.add("Merge", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Merge(
      bf.buildChild(value, "merge"),
      bf.buildChild(value, "with"),
      bf.buildChildOpt(value, "lambda")
    )
}

case class Reduce(
  reduce: Expr,
  initial: Expr,
  collection: Expr
) extends Expr {
  override val formKeys = Reduce.formKeys
}

object Reduce {

  val formKeys: List[Form.Key] =
    List("reduce", "initial", "collection").map(Form.Key.Required(_))
  Form.add("Reduce", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Reduce(
      bf.buildChild(value, "reduce"),
      bf.buildChild(value, "initial"),
      bf.buildChild(value, "collection")
    )
}

case class ForEach(
  foreach: Expr,
  collection: Expr
) extends Expr {
  override val formKeys = ForEach.formKeys
}

object ForEach {

  val formKeys: List[Form.Key] =
    List("foreach", "collection").map(Form.Key.Required(_))
  Form.add("ForEach", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ForEach(bf.buildChild(value, "foreach"), bf.buildChild(value, "collection"))
}

case class Filter(
  collection: Expr,
  filter: Expr
) extends Expr {
  override val formKeys = Filter.formKeys
}

object Filter {

  val formKeys: List[Form.Key] =
    List("collection", "filter").map(Form.Key.Required(_))
  Form.add("Filter", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Filter(bf.buildChild(value, "collection"), bf.buildChild(value, "filter"))
}

case class MapFn(collection: Expr, map: Expr) extends Expr {
  override val formKeys = MapFn.formKeys

  override def name: String = "Map"
}

object MapFn {
  val formKeys: List[Form.Key] = List("collection", "map").map(Form.Key.Required(_))
  Form.add("Map", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    MapFn(bf.buildChild(value, "collection"), bf.buildChild(value, "map"))

}

case class Join(join: Expr, `with`: Expr) extends Expr {
  override val formKeys = Join.formKeys
}

object Join {
  val formKeys: List[Form.Key] = List("join", "with").map(Form.Key.Required(_))
  Form.add("Join", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Join(bf.buildChild(value, "join"), bf.buildChild(value, "with"))
}

case class Reverse(reverse: Expr) extends Expr {
  override val formKeys = Reverse.formKeys

  override def effect: Effect = effect(Effect.Read)
}

object Reverse {
  val formKeys: List[Form.Key] = List("reverse").map(Form.Key.Required(_))
  Form.add("Reverse", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Reverse(bf.buildChild(value, "reverse"))
}
