package dsl

import solver.expressions.Sum
import solver.expressions.And
import solver.expressions.Or
import solver.expressions.LeZero

class SumDsl(sumParam: Sum) {

  private val sum: Sum = sumParam;

  def >=(sumParam: Sum): Constraint = {
    new Constraint(new LeZero((sum - sumParam).neg))
  }

  def <=(sumParam: Sum): Constraint = {
    new Constraint(new LeZero((sum - sumParam)))
  }

  def equal(sumParam: Sum): Constraint = {
    new Constraint(And(new LeZero((sum - sumParam).neg), new LeZero((sum - sumParam))))
  }

  def neq(sumParam: Sum): Constraint = {
    new Constraint(Or(new LeZero((sum - sumParam).neg).unary_!, new LeZero(sum - sumParam).unary_!))
  }

}

object SumDsl {

  implicit def Int2SumDsl(value: Int) = {
    new SumDsl(Sum(value))
  }

  implicit def Sum2SumDsl(value: Sum) = {
    new SumDsl(value)
  }

  implicit def Int2Sum(value: Int) = {
    Sum(value)
  }

}

