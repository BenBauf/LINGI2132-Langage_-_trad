package dsl

import solver.expressions._

class Constraint(lit: Literal) {

  val literal = lit

  def &(c: Constraint): Constraint = {
    new Constraint(And(this.literal, c.literal))
  }

  def |(c: Constraint): Constraint = {
    new Constraint(Or(this.literal, c.literal))
  }

}

object Constraint {
  def apply(lit: Literal) = {
    val x = new Constraint(lit)
    SolverDSL addConstraint (x)
    x
  }

  def >>(sum: Sum): Constraint = {
    new Constraint(LeZero(sum))
  }

  def <<(sum: Sum): Constraint = {
    new Constraint(LeZero(sum.neg))
  }

  /*implicit def SumDsl2Constraint(value: SumDsl) = {
    new Constraint(value)
  }*/

  /*implicit def Int2Constraint(value: Int) = {
    new Constraint(value)
    //new RangeVal(value, 0 to 1)
  }*/
}
