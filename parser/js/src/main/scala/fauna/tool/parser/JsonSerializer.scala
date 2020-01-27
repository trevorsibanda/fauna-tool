package fauna.tool.parser

import fauna.tool.ast._
import ujson._
import scala.util.Try

class uJsonSerializer extends fauna.tool.ast.Serializer[Value] {

  override def decode(v: Value): Option[Expr] = ???

  def valueFromString(v: String): Option[Value] =
    Try {
      ujson.read(v)
    }.toOption

  //TODO: Replace this with better alternative
  private def isNumber(s: String): Boolean = s.matches("[+-]?\\d+.?\\d+")

  def readValuePath(v: Value, path: Seq[String]): Option[Value] =
    Try {
      path.foldLeft(v)((accum, pos) => {
        //support numeric array indexes by attempting to typecast to int 
        pos.toIntOption.fold(accum(pos))(int => accum(int))
      })
    }.toOption

  override def encode(e: Expr): Option[Value] =
    if (e == null) None else Some(toJson(e))

  def encodeString(e: Expr): String = encode(e) match {
    case None    => "NOT_VALID"
    case Some(j) => j.render()
  }

  private def toJson(e: Expr): Value = e match {
    case ObjectExpr(ObjectL(m)) => Obj.from(Map("object" -> mapToJObject(m)))
    case l: Literal             => literalToJson(l)
    case e: Expr if e.arity == Arity.VarArgs =>
      Obj.from(Map(e.formKeys.head.key -> Arr.from(e.children.collect {
        case Some(x) => toJson(x)
      })))
    case e: Expr =>
      Obj.from(
        e.formKeys
          .zip(e.children)
          .collect {
            case (fk, Some(expr)) => (fk.key, encode(expr).getOrElse(ujson.Null))
            //case (fk, None)       => (fk.key, JNothing)
          }
      )
  }

  private def literalToJson(l: Literal): Value = l match {
    case LongL(l)    => Num(l)
    case DoubleL(d)  => Num(d)
    case FloatL(f)   => Num(f.toDouble)
    case DecimalL(d) => Num(d.toDouble)
    case IntL(i)     => Num(i.toDouble)
    case ArrayL(l) => {
      val ab = scala.collection.mutable.ArrayBuffer[Value]()
      l.foreach(item => ab.addOne(encode(item).getOrElse(ujson.Null)))
      Arr(ab)
    }
    case ObjectL(m) => mapToJObject(m)
    case StringL(s) => Str(s)
    case TrueL      => ujson.True
    case FalseL     => ujson.False
    case NullL      => ujson.Null
  }

  private def mapToJObject(m: Map[String, Expr]): Obj = {
    val lhm = scala.collection.mutable.LinkedHashMap[String, Value]()
    m.foreach {
      case (k, v) => lhm.addOne((k, encode(v).getOrElse(ujson.Null)))
    }
    Obj(lhm)
  }

}
