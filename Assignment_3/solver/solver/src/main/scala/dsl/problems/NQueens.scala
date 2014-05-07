package dsl.problems

import dsl._
import dsl.SumDsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum

class NQueens(n: Int) extends Problem {
  s = new SolverDSL(n)
  private val nQueens = n
  val queens = 0 until nQueens

  override def compute() {
    this.isComputed = true
    s.assigned(i => {
      "queen" + (i + 1) -> (1 to nQueens)
    })

    val upDiags = queens.map(i => s.variable(i) - i)
    val doDiags = queens.map(i => s.variable(i) + i)

    for (q1 <- queens; q2 <- queens; if q1 < q2) {
      s.addConstraint(s.variable(q1) !== s.variable(q2))
      s.addConstraint(upDiags(q1) !== upDiags(q2))
      s.addConstraint(doDiags(q1) !== doDiags(q2))
    }
  }

}