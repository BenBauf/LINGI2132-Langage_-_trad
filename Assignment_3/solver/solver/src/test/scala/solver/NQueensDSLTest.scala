package solver

import org.scalatest._
import dsl._
import dsl.SumDsl._
import dsl.problems.NQueens
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.Or
import solver.expressions.LeZero
import solver.expressions.IntVar

class NQueensDSLTest extends FlatSpec with Matchers {

  "NQueens" should "work" in {
    val nQueens = 10
    val queens = 0 until nQueens

    var s = SolverDSL
    s.init

    for (i <- 0 until nQueens) {
      "queen" + (i + 1) -> (1 to nQueens)
    }

    val upDiags = queens.map(i => s.getItem("queen%", i + 1) - i)
    val doDiags = queens.map(i => s.getItem("queen%", i + 1) + i)

    for (q1 <- queens; q2 <- queens; if q1 < q2) {
      s.getItem("queen%", q1 + 1) !== s.getItem("queen%", q2 + 1)
      upDiags(q1) !== upDiags(q2)
      doDiags(q1) !== doDiags(q2)
    }

    if (s.solve) println(s.solution)
    else println("infeasible")
  }

  "NQueens encapsulation" should "work" in {
    val nQueens = 10
    val s = new NQueens(nQueens)

    if (s.solveProblem) println(s.solution)
    else println("infeasible")
  }

}