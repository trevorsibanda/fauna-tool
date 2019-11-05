package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

import org.json4s.JsonAST.JValue

//conversion
case class ToString(to_string: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToString(bf.buildChild(value, "to_string"))
}

case class ToNumber(to_number: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToNumber(bf.buildChild(value, "to_number"))
}

case class ToTime(to_time: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToTime(bf.buildChild(value, "to_time"))
}

case class ToDate(to_date: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToDate(bf.buildChild(value, "to_date"))
}

case class ToObject(to_object: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToObject(bf.buildChild(value, "to_object"))
}

case class ToArray(to_array: Expr) extends FnExpr {

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ToArray(bf.buildChild(value, "to_array"))
}
