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

  def sum(): Constraint = {
    ???
  }

}

object Constraint {
  def apply(lit: Literal) = new Constraint(lit)

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
