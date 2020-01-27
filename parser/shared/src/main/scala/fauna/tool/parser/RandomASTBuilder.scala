package fauna.tool.parser

import fauna.tool.ast.{ Builder, Expr, Form, Literal, NullL }
/*
import fauna.tool.fuzzer.BasicRandGen

import scala.util.Random

class RandomBuilder(maxDepth: Int = 1) extends Builder[Expr] {

  val rand = new BasicRandGen(Expr.knownExprs)

  override def buildChild(parent: Expr, childName: String): Expr =
    rand.random match {
      case l: Literal => l
      case e: Expr    => e.build(e)(this)
    }

  override def readChild(parent: Expr, childName: String) = parent

  override def buildChildOpt(
    parent: Expr,
    childName: String
  ): Option[Expr] =
    if (Random.nextBoolean) Some(buildChild(parent, childName)) else None

  override def extractLiteral(value: Expr): Literal = rand.randomLiteral

  override def matchKeys(
    value: Expr,
    keys: Set[String]
  ): Option[Form.FormBuilder[scala.Any]] = ???

  override def buildOpt(value: Expr): Option[Expr] = ???

  override def build(value: Expr): Expr = value match {
    case l: Literal => l
    case e: Expr    => e.build(e)(this)
  }

}
 */
