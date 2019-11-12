package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

//Comparison
case class LT(lt: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    LT(bf.buildChild(value, "lt"))
}

case class LTE(lte: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    LTE(bf.buildChild(value, "lte"))
}

case class GT(gt: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    GT(bf.buildChild(value, "gt"))
}

case class GTE(gte: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    GTE(bf.buildChild(value, "gte"))
}

case class Equals(equals: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Equals(bf.buildChild(value, "equals"))
}

case class Contains(contains: Expr, in: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Contains(bf.buildChild(value, "contains"), bf.buildChild(value, "in"))
}
