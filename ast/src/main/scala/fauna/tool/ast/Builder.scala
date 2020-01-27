package fauna.tool.ast

abstract class Builder[T] {

  def matchKeys(value: T, keys: Set[String]): Option[Form.FormBuilder[scala.Any]]

  def build(value: T): Expr

  def build(value: String): Expr = ???

  def parse(value: String): T = ???

  def readChild(parent: T, childName: String): T

  def buildChild(parent: T, childName: String): Expr

  def buildChildOpt(parent: T, childName: String): Option[Expr]

  def buildOpt(value: T): Option[Expr]

  def extractLiteral(value: T): Literal

}
