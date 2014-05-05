package dsl

import solver.expressions.Sum
import solver.expressions.And
import solver.expressions.LeZero

class SumDsl(sumParam: Sum) {

  private val sum: Sum = sumParam;

  def >=(sumParam: Sum): Constraint = {
    new Constraint(new LeZero((sum - sumParam).neg))
  }

  def <=(sumParam: Sum): Constraint = {
    new Constraint(new LeZero((sum - sumParam)))
  }

  def ==(sumParam: Sum): Constraint = {
    new Constraint(And(new LeZero((sum - sumParam).neg), new LeZero((sum - sumParam))))
    ???
  }

}

object SumDsl {

  implicit def Int2SumDsl(value: Int) = {
    new SumDsl(Sum(value))
  }

}