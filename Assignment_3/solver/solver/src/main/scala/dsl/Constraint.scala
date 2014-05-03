package dsl

import solver.expressions._

class Constraint(lit: Literal) {

  val literal = lit

  def &(c: Constraint): Constraint = {
    new Constraint(And(this.literal, c.literal))
  }

}

object Constraint {
  def apply(lit: Literal) = new Constraint(lit)

  def >>(sum: Sum): Constraint = {
    new Constraint(LeZero(sum))
  }
}
