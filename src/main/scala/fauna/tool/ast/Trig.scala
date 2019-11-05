package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

//trig
case class ACos(acos: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ACos(bf.buildChild(value, "acos"))
}

case class ASin(asin: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ASin(bf.buildChild(value, "asin"))
}

case class ATan(atan: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ATan(bf.buildChild(value, "atan"))
}

case class Cos(cos: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Cos(bf.buildChild(value, "cos"))
}

case class Cosh(cosh: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Cosh(bf.buildChild(value, "cosh"))
}

case class Degrees(degrees: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Degrees(bf.buildChild(value, "degrees"))
}

case class Exp(exp: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Exp(bf.buildChild(value, "exp"))
}

case class Hypot(hypot: Expr, b: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Hypot(bf.buildChild(value, "hypot"), bf.buildChildOpt(value, "b"))
}

case class Ln(ln: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Ln(bf.buildChild(value, "ln"))
}

case class Log(log: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Log(bf.buildChild(value, "log"))
}

case class Pow(pow: Expr, exp: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Pow(bf.buildChild(value, "pow"), bf.buildChildOpt(value, "exp"))
}

case class Radians(radians: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Radians(bf.buildChild(value, "radians"))
}

case class Sin(sin: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Sin(bf.buildChild(value, "sin"))
}

case class Sinh(sinh: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Sinh(bf.buildChild(value, "sinh"))
}

case class Tan(tan: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Tan(bf.buildChild(value, "tan"))
}

case class Tanh(tanh: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Tanh(bf.buildChild(value, "tanh"))
}
