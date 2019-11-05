package fauna.tool.ast

trait Arity

object Arity {
  case class Exact(n: Int) extends Arity
  case class Between(min: Int, max: Int) extends Arity
  case object VarArgs extends Arity
}
