package fauna.tool.ast

import org.json4s.JsonAST.{
  JArray,
  JBool,
  JDecimal,
  JDouble,
  JField,
  JInt,
  JLong,
  JNothing,
  JNull,
  JObject,
  JString,
  JValue
}
import org.json4s._
import org.json4s.native.JsonMethods._

class JsonSerializer extends fauna.tool.ast.Serializer[JValue] {

  override def decode(v: JValue): Option[Expr] = ???

  override def encode(e: Expr): Option[JValue] =
    if (e == null) None else Some(toJson(e))

  def encodeString(e: Expr): String = encode(e) match {
    case None    => "NOT_VALID"
    case Some(j) => pretty(render(j))
  }

  private def toJson(e: Expr): JValue = e match {
    case ObjectExpr(ObjectL(m)) => JObject(JField("object", mapToJObject(m)))
    case l: Literal             => literalToJson(l)
    case e: Expr if e.arity == Arity.VarArgs =>
      JObject(JField(e.formKeys.head.key, JArray(e.children.collect {
        case Some(x) => toJson(x)
      }.toList)))
    case e: Expr =>
      JObject(
        e.formKeys
          .zip(e.children)
          .collect {
            case (fk, Some(expr)) => JField(fk.key, encode(expr).getOrElse(JNothing))
            case (fk, None)       => JField(fk.key, JNothing)
          }
          .toList
      )
  }

  private def literalToJson(l: Literal): JValue = l match {
    case LongL(l)    => JLong(l)
    case DoubleL(d)  => JDouble(d)
    case FloatL(f)   => JDecimal(f.toDouble)
    case DecimalL(d) => JDecimal(d)
    case IntL(i)     => JInt(i)
    case ArrayL(l)   => JArray(l.map(encode(_).getOrElse(JNothing)))
    case ObjectL(m)  => mapToJObject(m)
    case StringL(s)  => JString(s)
    case TrueL       => JBool(true)
    case FalseL      => JBool(false)
    case NullL       => JNull
  }

  private def mapToJObject(m: Map[String, Expr]): JObject =
    JObject(m.map {
      case (k, v) => JField(k, encode(v).getOrElse(JNothing))
    }.toList)

}
