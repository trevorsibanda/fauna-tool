package fauna.tool.ast

trait Type {

  def +(t: Type): Type = (this, t) match {
    case (AnyT, _) => AnyT
    case (_, AnyT) => AnyT
    case (myType, givenType) =>
      if (myType == givenType) myType
      else {
        givenType match {
          case ArrayT(t) => t + myType
          case _         => AnyT
        }
      }
  }
}

case object AnyT extends Type
case object StringT extends Type
case object BooleanT extends Type
case object NumericT extends Type
case class ArrayT(t: Type) extends Type
case object NullT extends Type
case object ObjectT extends Type
case object RefT extends Type
