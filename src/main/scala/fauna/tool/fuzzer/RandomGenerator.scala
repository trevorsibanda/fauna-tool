package fauna.tool.fuzzer

import fauna.tool.ast.{ Expr, Literal }
import fauna.tool.ast.Arity

trait RandomGenerator {
  def randomLiteral: Literal

  def randomPureFn: Expr

  def randomReadFn: Expr

  def randomWriteFn: Expr

  def randomExpr: Expr

  def literals: Seq[Literal]

  def readFns: Seq[Expr]

  def writeFns: Seq[Expr]

  def pureFns: Seq[Expr]

  def dynamicFns: Seq[Expr]

  def withArity(arity: Arity): Seq[Expr]
}
