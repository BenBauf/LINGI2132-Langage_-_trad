package dsl

import solver.expressions._

class Constraint(lit: Literal) {

  val literal = lit

  def &(c: Constraint): Constraint = {
    SolverDSL.rmConstraint(c)
    SolverDSL.rmConstraint(this)
    Constraint(And(this.literal, c.literal))
  }

  def |(c: Constraint): Constraint = {
    SolverDSL.rmConstraint(c)
    SolverDSL.rmConstraint(this)
    Constraint(Or(this.literal, c.literal))
  }

}

object Constraint {
  def apply(lit: Literal) = {
    val x = new Constraint(lit)
    SolverDSL.addConstraint(x)
    x
  }
}
