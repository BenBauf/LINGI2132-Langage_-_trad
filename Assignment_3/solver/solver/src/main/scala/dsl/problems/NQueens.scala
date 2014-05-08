package dsl.problems

import dsl._
import dsl.SumDsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum

class NQueens(n: Int) extends Problem {
  private val nQueens = n
  val queens = 0 until nQueens

  override def compute() {
    s.init
    this.isComputed = true
    implicit val v = "queen%"

    for (i <- 0 until nQueens) {
      "queen" + (i + 1) -> (1 to nQueens)
    }

    val upDiags = queens.map(i => s.getItem(i + 1) - i)
    val doDiags = queens.map(i => s.getItem(i + 1) + i)

    for (q1 <- queens; q2 <- queens; if q1 < q2) {
      s.getItem(q1 + 1) dif s.getItem(q2 + 1)
      upDiags(q1) dif upDiags(q2)
      doDiags(q1) dif doDiags(q2)
    }
  }

}