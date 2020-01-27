package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//import fauna.tool.validator.Constraint

//Numerics

case class Abs(abs: Expr) extends Expr {
  override val formKeys = Abs.formKeys

  override def evaluatesTo: Type = abs.evaluatesTo
}

object Abs {
  val formKeys: List[Form.Key] = List("abs").map(Form.Key.Required(_))
  Form.add("Abs", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Abs(bf.buildChild(value, "abs"))
  /*
  override def constraints: Map[String, Set[Constraint]] =
    Map("abs" -> Set(Constraint.EvalsTo(NumericT, ArrayT(NumericT))))
 */

}

case class Add(add: Expr) extends Expr {
  override val formKeys = Add.formKeys

  override def evaluatesTo: Type = add.evaluatesTo

  override def arity = Arity.VarArgs
}

object Add {
  val formKeys: List[Form.Key] = List("add").map(Form.Key.Required(_))
  Form.add("Add", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Add(bf.buildChild(value, "add"))
  /*
  override def constraints: Map[String, Set[Constraint]] =
    Map("add" -> Set(Constraint.EvalsTo(NumericT, ArrayT(NumericT))))
 */

}

case class BitAnd(bitand: Expr) extends Expr {
  override val formKeys = BitAnd.formKeys

  override def arity = Arity.VarArgs
}

object BitAnd {
  val formKeys: List[Form.Key] = List("bitand").map(Form.Key.Required(_))
  Form.add("BitAnd", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    BitAnd(bf.buildChild(value, "bitand"))

}

case class BitNot(bitnot: Expr) extends Expr {
  override val formKeys = BitNot.formKeys

  override def arity = Arity.VarArgs
}

object BitNot {
  val formKeys: List[Form.Key] = List("bitnot").map(Form.Key.Required(_))
  Form.add("BitNot", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    BitNot(bf.buildChild(value, "bitnot"))

}

case class BitOr(bitor: Expr) extends Expr {
  override val formKeys = BitOr.formKeys

  override def arity = Arity.VarArgs
}

object BitOr {
  val formKeys: List[Form.Key] = List("bitor").map(Form.Key.Required(_))
  Form.add("BitOr", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    BitOr(bf.buildChild(value, "bitor"))

}

case class BitXor(bitxor: Expr) extends Expr {
  override val formKeys = BitXor.formKeys

  override def arity = Arity.VarArgs
}

object BitXor {
  val formKeys: List[Form.Key] = List("bitxor").map(Form.Key.Required(_))
  Form.add("BitXor", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    BitXor(bf.buildChild(value, "bitxor"))

}

case class Ceil(ceil: Expr) extends Expr {
  override val formKeys = Ceil.formKeys
}

object Ceil {
  val formKeys: List[Form.Key] = List("ceil").map(Form.Key.Required(_))
  Form.add("Ceil", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Ceil(bf.buildChild(value, "ceil"))
}

case class Divide(divide: Expr) extends Expr {
  override val formKeys = Divide.formKeys

  override def arity = Arity.VarArgs
}

object Divide {
  val formKeys: List[Form.Key] = List("divide").map(Form.Key.Required(_))
  Form.add("Divide", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Divide(bf.buildChild(value, "divide"))

}

case class Floor(floor: Expr) extends Expr {
  override val formKeys = Floor.formKeys
}

object Floor {
  val formKeys: List[Form.Key] = List("floor").map(Form.Key.Required(_))
  Form.add("Floor", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Floor(bf.buildChild(value, "floor"))
}

case class Max(max: Expr) extends Expr {
  override val formKeys = Max.formKeys

  override def arity = Arity.VarArgs
}

object Max {
  val formKeys: List[Form.Key] = List("max").map(Form.Key.Required(_))
  Form.add("Max", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Max(bf.buildChild(value, "max"))

}

case class Min(min: Expr) extends Expr {
  override val formKeys = Min.formKeys

  override def arity = Arity.VarArgs
}

object Min {
  val formKeys: List[Form.Key] = List("min").map(Form.Key.Required(_))
  Form.add("Min", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Min(bf.buildChild(value, "min"))

}

case class Modulo(modulo: Expr) extends Expr {
  override val formKeys = Modulo.formKeys

  override def arity = Arity.VarArgs
}

object Modulo {
  val formKeys: List[Form.Key] = List("modulo").map(Form.Key.Required(_))
  Form.add("Modulo", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Modulo(bf.buildChild(value, "modulo"))

}

case class Multiply(multiply: Expr) extends Expr {
  override val formKeys = Multiply.formKeys

  override def arity = Arity.VarArgs
}

object Multiply {
  val formKeys: List[Form.Key] = List("multiply").map(Form.Key.Required(_))
  Form.add("Multiply", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Multiply(bf.buildChild(value, "multiply"))

}

case class Round(
  round: Expr,
  precision: Option[Expr]
) extends Expr {
  override val formKeys = Round.formKeys
}

object Round {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("round"), Form.Key.Optional("precision"))
  Form.add("Round", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Round(bf.buildChild(value, "round"), bf.buildChildOpt(value, "precision"))
}

case class Subtract(subtract: Expr) extends Expr {
  override val formKeys = Subtract.formKeys

  override def arity = Arity.VarArgs
}

object Subtract {
  val formKeys: List[Form.Key] = List("subtract").map(Form.Key.Required(_))
  Form.add("Subtract", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Subtract(bf.buildChild(value, "subtract"))

}

case class Sign(sign: Expr) extends Expr {
  override val formKeys = Sign.formKeys
}

object Sign {
  val formKeys: List[Form.Key] = List("sign").map(Form.Key.Required(_))
  Form.add("Sign", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Sign(bf.buildChild(value, "sign"))
}

case class Sqrt(sqrt: Expr) extends Expr {
  override val formKeys = Sqrt.formKeys
}

object Sqrt {
  val formKeys: List[Form.Key] = List("sqrt").map(Form.Key.Required(_))
  Form.add("Sqrt", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Sqrt(bf.buildChild(value, "sqrt"))
}

case class Trunc(
  trunc: Expr,
  precision: Option[Expr]
) extends Expr {
  override val formKeys = Trunc.formKeys
}

object Trunc {

  val formKeys: List[Form.Key] = List("trunc").map(Form.Key.Required(_)) ++ List(
    Form.Key.opt("precision")
  )
  Form.add("Trunc", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Trunc(bf.buildChild(value, "trunc"), bf.buildChildOpt(value, "precision"))
}

case class Mean(mean: Expr) extends Expr {
  override val formKeys = Mean.formKeys

  override def arity = Arity.VarArgs
}

object Mean {
  val formKeys: List[Form.Key] = List("mean").map(Form.Key.Required(_))
  Form.add("Mean", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Mean(bf.buildChild(value, "mean"))

}

case class Count(count: Expr) extends Expr {
  override val formKeys = Count.formKeys

  override def arity = Arity.VarArgs
}

object Count {
  val formKeys: List[Form.Key] = List("count").map(Form.Key.Required(_))
  Form.add("Count", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Count(bf.buildChild(value, "count"))

}

case class Sum(sum: Expr) extends Expr {
  override val formKeys = Sum.formKeys

  override def arity = Arity.VarArgs
}

object Sum {
  val formKeys: List[Form.Key] = List("sum").map(Form.Key.Required(_))
  Form.add("Sum", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Sum(bf.buildChild(value, "sum"))

}

case class Any(any: Expr) extends Expr {
  override val formKeys = Any.formKeys

  override def arity = Arity.VarArgs
}

object Any {
  val formKeys: List[Form.Key] = List("any").map(Form.Key.Required(_))
  Form.add("Any", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Any(bf.buildChild(value, "any"))

}

case class All(all: Expr) extends Expr {
  override val formKeys = All.formKeys

  override def arity = Arity.VarArgs
}

object All {
  val formKeys: List[Form.Key] = List("all").map(Form.Key.Required(_))
  Form.add("All", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    All(bf.buildChild(value, "all"))

}
