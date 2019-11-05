package fauna.tool.ast

import fauna.tool.parser.ASTBuilder

trait Literal extends Expr {
  override def children: Seq[Option[Expr]] = Seq()

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr = ???
}

case class ObjectExpr(`object`: Expr) extends Expr {
  override def toString = `object`.toString

  override def build[T](value: T)(implicit bf: ASTBuilder[T]): Expr =
    ObjectExpr(bf.buildChild(value, "object"))

}

case class ObjectL(m: Map[String, Expr]) extends Literal {
  override def evaluatesTo: Type = ObjectT
}

case class StringL(s: String) extends Literal {
  override def evaluatesTo: Type = StringT
}

case object NullL extends Literal {
  override def evaluatesTo: Type = NullT
}

abstract class NumericL[V](v: V) extends Literal {
  override def evaluatesTo: Type = NumericT
}

case class DoubleL(d: Double) extends NumericL(d)
case class IntL(i: BigInt) extends NumericL(i)
case class LongL(l: Long) extends NumericL(l)
case class FloatL(f: Float) extends NumericL(f)
case class DecimalL(d: BigDecimal) extends NumericL(d)

abstract class BooleanL(b: Boolean) extends Literal {
  override def evaluatesTo: Type = BooleanT
}

object BooleanL {
  def apply(b: Boolean): BooleanL = if (b) TrueL else FalseL
}
case object TrueL extends BooleanL(true)
case object FalseL extends BooleanL(false)

case class ArrayL(l: List[Expr]) extends Literal {

  override def evaluatesTo: Type = {
    val t: Type = AnyT
    if (l.isEmpty) t
    else
      l.map { _.evaluatesTo }.fold(l.head.evaluatesTo) {
        case (t1, t2) => t1 + t2
      }
  }
}
