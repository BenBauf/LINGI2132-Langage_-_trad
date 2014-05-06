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

  /*override def mustCompute(): Boolean = {
    !this.isComputed
  }*/

  override def compute() {
    s.assigned(i => {
      "queen" + (i + 1) -> (1 to nQueens)
    })

    //val solver = new Solver

    val upDiags = queens.map(i => s.variable(i) - i)
    val doDiags = queens.map(i => s.variable(i) + i)

    for (q1 <- queens; q2 <- queens; if q1 < q2) {

      s.addConstraint {
        val sum = s.variable(q1) - s.variable(q2)
        0 >= (sum + 1) | 0 >= (sum.neg + 1)
      }

      s.addConstraint {
        val sum = upDiags(q1) - upDiags(q2)
        0 >= (sum + 1) | 0 >= (sum.neg + 1)
      }

      s.addConstraint {
        val sum = doDiags(q1) - doDiags(q2)
        0 >= (1 + sum) | 0 >= (sum.neg + 1)
      }
    }
  }

}