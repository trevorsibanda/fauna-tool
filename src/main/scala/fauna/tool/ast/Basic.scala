package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

trait FnExpr extends Expr

case class UnknownExpression[T](value: T) extends Expr {
  override def children: Seq[Option[Expr]] = Seq()
}

case object Undefined extends Expr {
  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = ???

  override def children: Seq[Option[Expr]] = Seq()
}

case class Body(expr: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Body(bf.buildChild(value, "expr"))
}

case class Lambda(lambda: Expr, expr: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Lambda(bf.buildChild(value, "lambda"), bf.buildChild(value, "expr"))
}

case class Call(call: Expr, arguments: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Call(bf.buildChild(value, "call"), bf.buildChildOpt(value, "arguments"))
}

case class Do(`do`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Do(bf.buildChild(value, "do"))
}

case class If(`if`: Expr, then: Expr, `else`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    If(
      bf.buildChild(value, "if"),
      bf.buildChild(value, "then"),
      bf.buildChild(value, "else")
    )
}

case class Let(let: Expr, in: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Let(bf.buildChild(value, "let"), bf.buildChild(value, "in"))
}

case class Var(`var`: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Var(bf.buildChild(value, "var"))
}

case class At(at: Expr, expr: Expr) extends Expr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    At(bf.buildChild(value, "at"), bf.buildChild(value, "expr"))
}

case class Abort(abort: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Abort(bf.buildChild(value, "abort"))
}
