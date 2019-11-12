package fauna.tool.parser

import fauna.tool.ast.{ Arity, Expr, Literal }

abstract class ASTBuilder[T] {
  type Builder = T => Expr
  type Accessors = List[(String, Boolean)]

  def register(
    name: String,
    arity: Arity,
    classAccessors: Accessors,
    builder: Builder
  ) = {
    if (registered.find {
          case (n: String, _: Arity, _: Accessors, _: Builder) => {
            if (n == name) true else false
          }
        }.isDefined) ()
    else {
      registered :+= (name, arity, classAccessors, builder)
    }

  }

  @volatile
  var registered: Array[(String, Arity, Accessors, Builder)] = Array()

  def matchKeys(value: T, classAccessors: Accessors): Boolean

  def build(value: T): Expr

  def buildChild(parent: T, childName: String): Expr

  def buildChildOpt(parent: T, childName: String): Option[Expr]

  def buildOpt(value: T): Option[Expr]

  def extractLiteral(value: T): Literal

}
