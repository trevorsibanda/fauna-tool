package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

//bool
case class And(and: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    And(bf.buildChild(value, "and"))
}

case class Or(or: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Or(bf.buildChild(value, "or"))
}

case class Not(not: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    Not(bf.buildChild(value, "not"))
}
