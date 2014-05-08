package solver

import org.scalatest._

import dsl._
import dsl.Constraint._
import dsl.RangeVal._
import dsl.SumDsl._
import solver.expressions.IntVar
import solver.expressions.Sum
import solver.core.SatSolver
import solver.core.encoder.OrderEncoder
import solver.expressions.IntVar
import solver.core.CSP
import solver.expressions.LeZero
import solver.expressions.Or
import solver.expressions.Sum
import solver.expressions.And
import solver.core.Assignment

class SudokuDSLTest extends FlatSpec with Matchers {

  "Sudoku" should "work" in {

    val maxVal = 9
    val checkSum = 45

    var s = SolverDSL
    for (i <- 0 until 81) {
      "item_" + i -> (0 to maxVal)
    }

    s.E(0 to 72, 9, i => {
      S(i to (i + 8), 1, "item_%") equal checkSum
    })

    s.E(0 to 8, 1, i => {
      S(i to (72 + i), 9, "item_%") equal checkSum
    })

    if (s.solve) println(s.solution)
    else println("infeasible")
  }

}