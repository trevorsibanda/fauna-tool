package fauna.tool.validator

import fauna.tool.ast.{ Expr, Type }

trait Constraint {
  def check(expr: Expr): ValidationResult = ???
}

object Constraint {
  type ConstraintFn = Any => ValidationResult //todo: choose data to pass
  case class EvalsTo(tpe: Type*) extends Constraint {

    override def check(expr: Expr) = {
      val t = expr.evaluatesTo
      if (tpe.contains(t)) ValidationSuccess
      else {
        ValidationError(expr.name, Seq(s"""Argument expected type: ${tpe.mkString(
          ","
        )} but found ${t}"""))
      }
    }
  }
  case class Or(constraints: Constraint*) extends Constraint
  case class And(constraints: Constraint*) extends Constraint
  case class Not(constraints: Constraint*) extends Constraint

  case class Pred(fn: ConstraintFn) extends Constraint {
    override def check(expr: Expr): ValidationResult = fn()
  }
}
