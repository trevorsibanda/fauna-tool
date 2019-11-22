package fauna.tool.parser

import fauna.tool.ast.{
  Expr,
  Literal,
  NullL
}

import fauna.tool.fuzzer.BasicRandGen

import scala.util.Random
import fauna.tool.ast.TrueL
import fauna.tool.ast.FalseL

class RandomASTBuilder(maxDepth: Int = 1) extends ASTBuilder[Expr] {

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

  override def matchKeys(value: Expr, classAccessors: Accessors): Boolean = ???

  override def buildOpt(value: Expr): Option[Expr] = ???

  override def build(value: Expr): Expr = value match {
    case NullL      => NullL
    case TrueL      => TrueL
    case FalseL     => FalseL
    case l: Literal => l
    case e: Expr    => e.build(e)(this)
  }

}
