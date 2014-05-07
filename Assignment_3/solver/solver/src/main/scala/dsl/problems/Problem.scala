package dsl.problems

import dsl._
import dsl.SumDsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum
import solver.core.Assignment

trait Problem extends SolverDSL {
  var isComputed = false

  def compute()

  def solveProblemWith(c: Constraint): Boolean = {
    if (!isComputed)
      compute
    return solveWith(c)
  }

  def solveProblem(): Boolean = {
    if (!isComputed)
      compute
    return solve
  }

}