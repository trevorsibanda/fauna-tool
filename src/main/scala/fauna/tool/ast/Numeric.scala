package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

import fauna.tool.validator.Constraint

//Numerics
case class Abs(abs: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Abs(bf.buildChild(value, "abs"))

  override def constraints: Map[String, Set[Constraint]] =
    Map("abs" -> Set(Constraint.EvalsTo(NumericT, ArrayT(NumericT))))

  override def evaluatesTo: Type = abs.evaluatesTo
}

case class Add(add: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Add(bf.buildChild(value, "add"))

  override def arity = Arity.VarArgs

  override def constraints: Map[String, Set[Constraint]] =
    Map("add" -> Set(Constraint.EvalsTo(NumericT, ArrayT(NumericT))))

  override def evaluatesTo: Type = add.evaluatesTo
}

case class BitAnd(bitand: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    BitAnd(bf.buildChild(value, "bitand"))

  override def arity = Arity.VarArgs
}

case class BitNot(bitnot: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    BitNot(bf.buildChild(value, "bitnot"))

  override def arity = Arity.VarArgs
}

case class BitOr(bitor: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    BitOr(bf.buildChild(value, "bitor"))

  override def arity = Arity.VarArgs
}

case class BitXor(bitxor: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    BitXor(bf.buildChild(value, "bitxor"))

  override def arity = Arity.VarArgs
}

case class Ceil(ceil: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Ceil(bf.buildChild(value, "ceil"))
}

case class Divide(divide: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Divide(bf.buildChild(value, "divide"))

  override def arity = Arity.VarArgs
}

case class Floor(floor: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Floor(bf.buildChild(value, "floor"))
}

case class Max(max: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Max(bf.buildChild(value, "max"))

  override def arity = Arity.VarArgs
}

case class Min(min: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Min(bf.buildChild(value, "min"))

  override def arity = Arity.VarArgs
}

case class Modulo(modulo: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Modulo(bf.buildChild(value, "modulo"))

  override def arity = Arity.VarArgs
}

case class Multiply(multiply: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Multiply(bf.buildChild(value, "multiply"))

  override def arity = Arity.VarArgs
}

case class Round(round: Expr, precision: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Round(bf.buildChild(value, "round"), bf.buildChildOpt(value, "precision"))
}

case class Subtract(subtract: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Subtract(bf.buildChild(value, "subtract"))

  override def arity = Arity.VarArgs

}

case class Sign(sign: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Sign(bf.buildChild(value, "sign"))
}

case class Sqrt(sqrt: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Sqrt(bf.buildChild(value, "sqrt"))
}

case class Trunc(trunc: Expr, precision: Option[Expr]) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Trunc(bf.buildChild(value, "trunc"), bf.buildChildOpt(value, "precision"))
}

case class Mean(mean: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Mean(bf.buildChild(value, "mean"))

  override def arity = Arity.VarArgs
}

case class Count(count: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Count(bf.buildChild(value, "count"))

  override def arity = Arity.VarArgs
}

case class Sum(sum: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Sum(bf.buildChild(value, "sum"))

  override def arity = Arity.VarArgs
}

case class Any(any: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Any(bf.buildChild(value, "any"))

  override def arity = Arity.VarArgs
}

case class All(all: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    All(bf.buildChild(value, "all"))

  override def arity = Arity.VarArgs
}
