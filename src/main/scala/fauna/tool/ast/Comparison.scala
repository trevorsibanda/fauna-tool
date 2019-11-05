package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

import org.json4s.JsonAST.JValue

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
