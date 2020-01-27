package fauna.tool.ast

import scala.scalajs.js.annotation._
import scala.annotation.meta.field

abstract class Literal extends Expr {
  override def children: Seq[Option[Expr]] = Seq()
}

case class ObjectExpr(`object`: Expr) extends Expr {
  override val formKeys = ObjectExpr.formKeys

  override def toString = `object`.toString

}

object ObjectExpr {
  val formKeys: List[Form.Key] = List("object").map(Form.Key.Required(_))
  Form.add("ObjectExpr", build _, formKeys: _*)

  def build[T](value: T, bf: Builder[T]): Expr =
    ObjectExpr(bf.extractLiteral(bf.readChild(value, "object")))

}

case class ObjectL(m: Map[String, Expr]) extends Literal {
  override def evaluatesTo: Type = ObjectT

  override def children: Seq[Option[Expr]] = m.values.map(Some(_)).toSeq
}

case class StringL(s: String) extends Literal {
  override def evaluatesTo: Type = StringT
}

case object NullL extends Literal {
  override def evaluatesTo: Type = NullT
}

abstract class NumericL[V](val v: V) extends Literal {
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

  override def children: Seq[Option[Expr]] = l.map(Some(_))
}

object ArrayL {
  def apply(literals: Expr*): ArrayL = ArrayL(literals.toList)
}
