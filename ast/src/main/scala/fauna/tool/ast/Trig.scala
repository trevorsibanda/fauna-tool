package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

//trig

case class ACos(acos: Expr) extends Expr {
  override val formKeys = ACos.formKeys
}

object ACos {
  val formKeys: List[Form.Key] = List("acos").map(Form.Key.Required(_))
  Form.add("ACos", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ACos(bf.buildChild(value, "acos"))
}

case class ASin(asin: Expr) extends Expr {
  override val formKeys = ASin.formKeys
}

object ASin {
  val formKeys: List[Form.Key] = List("asin").map(Form.Key.Required(_))
  Form.add("ASin", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ASin(bf.buildChild(value, "asin"))
}

case class ATan(atan: Expr) extends Expr {
  override val formKeys = ATan.formKeys
}

object ATan {
  val formKeys: List[Form.Key] = List("atan").map(Form.Key.Required(_))
  Form.add("ATan", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ATan(bf.buildChild(value, "atan"))
}

case class Cos(cos: Expr) extends Expr {
  override val formKeys = Cos.formKeys
}

object Cos {
  val formKeys: List[Form.Key] = List("cos").map(Form.Key.Required(_))
  Form.add("Cos", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Cos(bf.buildChild(value, "cos"))
}

case class Cosh(cosh: Expr) extends Expr {
  override val formKeys = Cosh.formKeys
}

object Cosh {
  val formKeys: List[Form.Key] = List("cosh").map(Form.Key.Required(_))
  Form.add("Cosh", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Cosh(bf.buildChild(value, "cosh"))
}

case class Degrees(degrees: Expr) extends Expr {
  override val formKeys = Degrees.formKeys
}

object Degrees {
  val formKeys: List[Form.Key] = List("degrees").map(Form.Key.Required(_))
  Form.add("Degrees", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Degrees(bf.buildChild(value, "degrees"))
}

case class Exp(exp: Expr) extends Expr {
  override val formKeys = Exp.formKeys
}

object Exp {
  val formKeys: List[Form.Key] = List("exp").map(Form.Key.Required(_))
  Form.add("Exp", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Exp(bf.buildChild(value, "exp"))
}

case class Hypot(hypot: Expr, b: Option[Expr]) extends Expr {
  override val formKeys = Hypot.formKeys
}

object Hypot {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("hypot"), Form.Key.Optional("b"))
  Form.add("Hypot", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Hypot(bf.buildChild(value, "hypot"), bf.buildChildOpt(value, "b"))
}

case class Ln(ln: Expr) extends Expr {
  override val formKeys = Ln.formKeys
}

object Ln {
  val formKeys: List[Form.Key] = List("ln").map(Form.Key.Required(_))
  Form.add("Ln", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Ln(bf.buildChild(value, "ln"))
}

case class Log(log: Expr) extends Expr {
  override val formKeys = Log.formKeys
}

object Log {
  val formKeys: List[Form.Key] = List("log").map(Form.Key.Required(_))
  Form.add("Log", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Log(bf.buildChild(value, "log"))
}

case class Pow(pow: Expr, exp: Option[Expr]) extends Expr {
  override val formKeys = Pow.formKeys
}

object Pow {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("pow"), Form.Key.Optional("exp"))
  Form.add("Pow", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Pow(bf.buildChild(value, "pow"), bf.buildChildOpt(value, "exp"))
}

case class Radians(radians: Expr) extends Expr {
  override val formKeys = Radians.formKeys
}

object Radians {
  val formKeys: List[Form.Key] = List("radians").map(Form.Key.Required(_))
  Form.add("Radians", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Radians(bf.buildChild(value, "radians"))
}

case class Sin(sin: Expr) extends Expr {
  override val formKeys = Sin.formKeys
}

object Sin {
  val formKeys: List[Form.Key] = List("sin").map(Form.Key.Required(_))
  Form.add("Sin", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Sin(bf.buildChild(value, "sin"))
}

case class Sinh(sinh: Expr) extends Expr {
  override val formKeys = Sinh.formKeys
}

object Sinh {
  val formKeys: List[Form.Key] = List("sinh").map(Form.Key.Required(_))
  Form.add("Sinh", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Sinh(bf.buildChild(value, "sinh"))
}

case class Tan(tan: Expr) extends Expr {
  override val formKeys = Tan.formKeys
}

object Tan {
  val formKeys: List[Form.Key] = List("tan").map(Form.Key.Required(_))
  Form.add("Tan", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Tan(bf.buildChild(value, "tan"))
}

case class Tanh(tanh: Expr) extends Expr {
  override val formKeys = Tanh.formKeys
}

object Tanh {
  val formKeys: List[Form.Key] = List("tanh").map(Form.Key.Required(_))
  Form.add("Tanh", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Tanh(bf.buildChild(value, "tanh"))
}
