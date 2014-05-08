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
      0, 0, 3, 6, 2, 5, 0, 0, 4,
      9, 7, 0, 0, 0, 3, 0, 5, 0,
      4, 0, 6, 0, 0, 0, 0, 0, 2,
      3, 0, 0, 0, 8, 0, 0, 6, 0,
      0, 1, 8, 0, 0, 0, 5, 4, 0,
      0, 6, 0, 0, 5, 0, 0, 0, 7,
      5, 0, 0, 0, 0, 0, 8, 0, 1,
      0, 9, 0, 5, 0, 0, 0, 2, 6,
      6, 0, 0, 9, 1, 8, 7, 0, 0)
    /*Array(
      1, 8, 3, 6, 2, 5, 0, 0, 4,
      9, 7, 2, 1, 4, 3, 0, 5, 8,
      4, 5, 6, 8, 7, 9, 3, 1, 2,
      3, 4, 5, 7, 8, 1, 2, 6, 9,
      7, 1, 8, 2, 9, 6, 0, 4, 3,
      2, 6, 9, 0, 5, 4, 1, 8, 7,
      5, 3, 7, 4, 6, 2, 8, 9, 1,
      8, 9, 1, 5, 3, 7, 4, 2, 6,
      6, 2, 0, 9, 1, 8, 7, 3, 5)*/

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
    implicit val v = "item_%"

    var s = SolverDSL
    for (i <- 0 until sudoku.length) {
      val value = sudoku(i)
      if (value == 0) {
        "item_" + i -> (1 to maxVal)
      } else {
        "item_" + i -> (value to value)
      }
    }
    //ligne
    s.E(0 to 72, 9, i => {
      val line = s.getSetVariables(i to (i + 8), 1)
      line !== line
    })
    //colonne
    s.E(0 to 8, 1, i => {
      val col = s.getSetVariables(i to (72 + i), 9)
      col !== col
    })

    //carré
    /*s.E(0 to 8, 1, i => {
      val line = s.getSetVariables(i to (72 + i), 9)
      line !== line
    })*/
    for (i <- Array(0, 3, 6, 27, 30, 33, 54, 57, 60)) {
      val l = Array(0 + i, 1 + i, 2 + i, 9 + i, 10 + i, 11 + i, 18 + i, 19 + i, 20 + i)
      println(l(0))
      val square = s.getSetVariable(l)
      square !== square
    }

    if (s.solve) println(s.solution)
    else println("infeasible")
  }

}