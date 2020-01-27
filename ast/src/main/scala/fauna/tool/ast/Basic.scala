package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

case class UnknownExpression[T](value: T) extends Expr {
  override def children: Seq[Option[Expr]] = Seq()
}

case object Undefined extends Expr {
  override def children: Seq[Option[Expr]] = Seq()
}

case class Body(expr: Expr) extends Expr {
  override val formKeys = Body.formKeys
}

object Body {
  val formKeys: List[Form.Key] = List("expr").map(Form.Key.Required(_))
  Form.add("Body", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Body(bf.buildChild(value, "expr"))
}

case class Lambda(lambda: Expr, expr: Expr) extends Expr {
  override val formKeys = Lambda.formKeys
}

object Lambda {
  val formKeys: List[Form.Key] = List("lambda", "expr").map(Form.Key.Required(_))
  Form.add("Lambda", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Lambda(bf.buildChild(value, "lambda"), bf.buildChild(value, "expr"))
}

case class Call(
  call: Expr,
  arguments: Option[Expr]
) extends Expr {
  override val formKeys = Call.formKeys
}

object Call {

  val formKeys: List[Form.Key] =
    List(Form.Key.Required("call"), Form.Key.Optional("arguments"))
  Form.add("Call", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Call(bf.buildChild(value, "call"), bf.buildChildOpt(value, "arguments"))
}

case class Do(`do`: Expr) extends Expr {
  override val formKeys = Do.formKeys
}

object Do {
  val formKeys: List[Form.Key] = List("do").map(Form.Key.Required(_))
  Form.add("Do", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Do(bf.buildChild(value, "do"))
}

case class If(
  `if`: Expr,
  then: Expr,
  `else`: Expr
) extends Expr {
  override val formKeys = If.formKeys
}

object If {
  val formKeys: List[Form.Key] = List("if", "then", "else").map(Form.Key.Required(_))
  Form.add("If", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    If(
      bf.buildChild(value, "if"),
      bf.buildChild(value, "then"),
      bf.buildChild(value, "else")
    )
}

case class Let(let: Expr, in: Expr) extends Expr {
  override val formKeys = Let.formKeys
}

object Let {
  val formKeys: List[Form.Key] = List("let", "in").map(Form.Key.Required(_))
  Form.add("Let", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Let(bf.buildChild(value, "let"), bf.buildChild(value, "in"))
}

case class Var(`var`: Expr) extends Expr {
  override val formKeys = Var.formKeys
}

object Var {
  val formKeys: List[Form.Key] = List("var").map(Form.Key.Required(_))
  Form.add("Var", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Var(bf.buildChild(value, "var"))
}

case class At(at: Expr, expr: Expr) extends Expr {
  override val formKeys = At.formKeys
}

object At {
  val formKeys: List[Form.Key] = List("at", "expr").map(Form.Key.Required(_))
  Form.add("At", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    At(bf.buildChild(value, "at"), bf.buildChild(value, "expr"))
}

case class Abort(abort: Expr) extends Expr {
  override val formKeys = Abort.formKeys
}

object Abort {
  val formKeys: List[Form.Key] = List("abort").map(Form.Key.Required(_))
  Form.add("Abort", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    Abort(bf.buildChild(value, "abort"))
}
