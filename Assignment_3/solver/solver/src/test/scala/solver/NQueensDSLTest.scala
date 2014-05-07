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

    var s = new SolverDSL(nQueens)
    s.assigned(i => {
      "queen" + (i + 1) -> (1 to nQueens)
    })

    //val solver = new Solver

    val upDiags = queens.map(i => s.variable(i) - i)
    val doDiags = queens.map(i => s.variable(i) + i)

    for (q1 <- queens; q2 <- queens; if q1 < q2) {
      s.addConstraint(s.variable(q1) !== s.variable(q2))
      s.addConstraint(upDiags(q1) !== upDiags(q2))
      s.addConstraint(doDiags(q1) !== doDiags(q2))
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