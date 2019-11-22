package fauna.tool.parser

import fauna.tool.ast.{ Arity, Expr }
import fauna.tool.ast.{ FalseL, Literal, TrueL }
import fauna.tool.ast.{
  ArrayL,
  DecimalL,
  DoubleL,
  IntL,
  LongL,
  NullL,
  ObjectL,
  StringL
}
import fauna.tool.ast.UnknownExpression

import org.json4s.JsonAST.{
  JArray,
  JBool,
  JDecimal,
  JDouble,
  JInt,
  JLong,
  JNothing,
  JNull,
  JObject,
  JString,
  JValue
}

class JsonASTBuilder() extends ASTBuilder[JValue] {

  override def matchKeys(value: JValue, classAccessors: Accessors): Boolean = {
    val jKeys = value.asInstanceOf[JObject].values.keySet
    val caOptKeys = classAccessors.collect { case (n: String, b: Boolean) if b => n }.toSet
    val caRequiredKeys = classAccessors.collect {
      case (n: String, b: Boolean) if !b => n
    }.toSet

    val additional = jKeys.diff(caRequiredKeys)
    additional.diff(caOptKeys).isEmpty

  }

  override def build(json: JValue): Expr = json match {
    case obj: JObject => {

      val keys = obj.values.keySet

      val builder = registered.collectFirst {
        case (_, Arity.Exact(n), acc, builder)
            if (keys.size == n && matchKeys(obj, acc)) =>
          builder
        case (_, Arity.Between(min, max), acc, builder)
            if (keys.size >= min && keys.size <= max && matchKeys(obj, acc)) =>
          builder
        case (_, Arity.VarArgs, acc, builder)
            if (keys.size >= 1 && matchKeys(obj, acc)) =>
          builder
      }

      def asLiteral: Expr = extractLiteral(obj)
      builder.fold(asLiteral)(_(obj))

    }
    case JNothing => UnknownExpression(json) //this should not happen
    case _        => extractLiteral(json)
  }

  override def buildOpt(json: JValue): Option[Expr] = json match {
    case JNothing => None
    case _        => Some(build(json))
  }

  override def buildChild(parent: JValue, childName: String): Expr =
    this.build(parent \ childName)

  override def buildChildOpt(parent: JValue, childName: String): Option[Expr] =
    this.buildOpt(parent \ childName)

  override def extractLiteral(json: JValue): Literal = json match {
    case JString(s)    => StringL(s)
    case JBool(value)  => if (value) TrueL else FalseL
    case JDecimal(num) => DecimalL(num)
    case JDouble(num)  => DoubleL(num)
    case JInt(num)     => IntL(num)
    case JNull         => NullL
    case JLong(num)    => LongL(num)
    case JObject(obj)  => parseBindings(json)
    case JArray(arr) => {
      val l: List[Expr] = arr.map(build)
      ArrayL(l)
    }
  }

  override def readChild(parent: JValue, childName: String): JValue =
    parent \ childName

  private[parser] def parseBindings(j: JValue): Literal =
    ObjectL(j match {
      case obj: JObject =>
        obj.values.keySet.map { key =>
          (key, this.build(obj \ key))
        }.toMap
      case JArray(arr) => {
        arr
          .collect { case v: JObject => this.build(v) }
          .collect {
            case oe: ObjectL => oe.m
          }
          .flatten
          .toMap
      }
      case _ => Map()
    })

}
