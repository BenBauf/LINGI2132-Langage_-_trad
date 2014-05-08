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

    val sudoku = Array(
      1, 8, 3, 6, 2, 5, 0, 0, 4,
      9, 7, 2, 1, 4, 3, 0, 5, 8,
      4, 5, 6, 8, 7, 9, 3, 1, 2,
      3, 4, 5, 7, 8, 1, 2, 6, 9,
      7, 1, 8, 2, 9, 6, 5, 4, 3,
      2, 6, 9, 3, 5, 4, 1, 8, 7,
      5, 3, 7, 4, 6, 2, 8, 9, 1,
      8, 9, 1, 5, 3, 7, 4, 2, 6,
      6, 2, 4, 9, 1, 8, 7, 3, 5)

    /*Array(
      1, 8, 3, 6, 2, 5, 9, 7, 4,
      9, 7, 2, 1, 4, 3, 6, 5, 8,
      4, 5, 6, 8, 7, 9, 3, 1, 2,
      3, 4, 5, 7, 8, 1, 2, 6, 9,
      7, 1, 8, 2, 9, 6, 5, 4, 3,
      2, 6, 9, 3, 5, 4, 1, 8, 7,
      5, 3, 7, 4, 6, 2, 8, 9, 1,
      8, 9, 1, 5, 3, 7, 4, 2, 6,
      6, 2, 4, 9, 1, 8, 7, 3, 5)*/

    val maxVal = 9
    val checkSum = 45

    var s = SolverDSL
    for (i <- 0 until sudoku.length) {
      val value = sudoku(i)
      if (value == 0) {
        "item_" + i -> (0 to maxVal)
      } else {
        "item_" + i -> (value to value)
      }
    }

    s.E(0 to 72, 9, i => {
      s.getSetVariables(i to (i + 8), "item_%", 1) !== s.getSetVariables(i to (i + 8), "item_%", 1)
      //S(i to (i + 8), 1, "item_%") equal checkSum
    })

    s.E(0 to 8, 1, i => {
      S(i to (72 + i), 9, "item_%") equal checkSum
    })

    if (s.solve) println(s.solution)
    else println("infeasible")
  }

}