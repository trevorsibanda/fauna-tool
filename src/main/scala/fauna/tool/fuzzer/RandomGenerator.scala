package fauna.tool.fuzzer

import fauna.tool.ast.{ Expr, Literal }
import fauna.tool.ast.Arity

trait RandomGenerator {
  def randomLiteral: Literal

  def randomPureFn: Expr

  def randomReadFn: Expr

  def randomWriteFn: Expr

  def random: Expr

  def withArity(arity: Arity): Seq[Expr]
}
