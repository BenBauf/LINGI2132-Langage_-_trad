package dsl

import solver.expressions.IntVar
import solver.expressions.Sum
import solver.expressions.And
import solver.expressions.Or
import solver.expressions.LeZero

class SumDsl(sumParam: Sum) {

  private val sum: Sum = sumParam;

  def >==(sumParam: Sum): Constraint = {
    Constraint(new LeZero((sum - sumParam).neg))
  }

  def <==(sumParam: Sum): Constraint = {
    Constraint(new LeZero((sum - sumParam)))
  }

  /*def ===(sumParam: Sum): Constraint = {
    Constraint(And(new LeZero((sum - sumParam).neg), new LeZero((sum - sumParam))))
  }*/

  def equal(sumParam: Sum): Constraint = {
    Constraint(And(new LeZero((sum - sumParam).neg), new LeZero((sum - sumParam))))
  }

  def !==(sumParam: Sum): Constraint = {
    Constraint(Or(And(LeZero(sum - sumParam), !LeZero(sumParam - sum)), And(LeZero(sumParam - sum), !LeZero(sum - sumParam))))
  }

}

object SumDsl {

  implicit def IntVar2Sum(value: IntVar) = {
    Sum(value)
  }

  implicit def IntVar2SumDsl(value: IntVar) = {
    new SumDsl(Sum(value))
  }

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

