package fauna.tool.parser

import fauna.tool.ast.{ Arity, Builder, Expr }
import fauna.tool.ast.{ FalseL, Literal, TrueL }
import fauna.tool.ast.{ ArrayL, DecimalL, DoubleL, Form, NullL, ObjectL, StringL }
import fauna.tool.ast.UnknownExpression

import ujson._
import scala.util.Try
import scala.util.Success
import fauna.tool.ast.IntL

class JsonBuilder() extends Builder[Value] {

  override def matchKeys(
    value: Value,
    keys: Set[String]
  ): Option[Form.FormBuilder[Any]] = Form.matches(keys)

  override def build(json: String): Expr = build(ujson.read(json))

  override def parse(json: String): Value = ujson.read(json)

  override def build(json: Value): Expr = json match {
    case ujson.Obj(map) =>
      matchKeys(json, map.keys.toSet) match {
        case Some(builder) => builder(json, this.asInstanceOf[Builder[scala.Any]])
        case None          => extractLiteral(json)
      }
    case _ => extractLiteral(json)
  }

  override def buildOpt(json: Value): Option[Expr] = json match {
    case null => None
    case _    => Some(build(json))
  }

  override def buildChild(parent: Value, childName: String): Expr =
    this
      .buildChildOpt(parent, childName)
      .getOrElse(UnknownExpression((parent, childName)))

  override def buildChildOpt(parent: Value, childName: String): Option[Expr] =
    Try {
      parent(childName)
    }.toOption.map(build)

  override def extractLiteral(json: Value): Literal = json match {
    case Str(s)      => StringL(s)
    case True        => TrueL
    case False       => FalseL
    case Bool(value) => if (value) TrueL else FalseL
    case Num(num)    => if ((num % 1) == 0) IntL(num.toLong) else DoubleL(num)
    case ujson.Null  => NullL
    case Arr(arrb) => {
      val l: List[Expr] = arrb.map(build).toList
      ArrayL(l)
    }
    case Obj(_) => parseBindings(json)
  }

  override def readChild(parent: Value, childName: String): Value =
    parent.obj(childName)

  private[parser] def parseBindings(j: Value): Literal =
    ObjectL(j match {
      case Obj(hashmap) =>
        hashmap.keySet.map { key =>
          (key, this.build(readChild(j, key)))
        }.toMap
      case Arr(arrb) => {
        arrb
          .collect { case v: ujson.Obj => this.build(v) }
          .collect {
            case oe: ObjectL => oe.m
          }
          .flatten
          .toMap
      }
      case _ => Map()
    })

}
