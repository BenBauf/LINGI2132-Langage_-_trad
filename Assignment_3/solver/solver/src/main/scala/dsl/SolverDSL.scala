package dsl

import solver.core.SatSolver
import solver.core.encoder.OrderEncoder
import solver.expressions.IntVar
import solver.core.CSP
import solver.Solver
import solver.expressions.LeZero
import solver.expressions.Or
import solver.expressions.Sum
import solver.expressions.And

class SolverDSL(length: Int) extends Solver {

  private val nItems = length
  private val items = 0 until nItems

  val assigned = new Array[Int](nItems)

  def assigned(body: Int => IntVar) {
    for (i <- 0 until nItems) {
      addVariable(body(i))
    }
  }

  def solveWith(c: Constraint): Boolean = {
    this.solveWith(c.literal)
  }

  def addConstraint(c: Constraint) {
    addConstraint(c.literal)
  }

}