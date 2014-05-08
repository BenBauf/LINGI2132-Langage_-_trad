package solver

import org.scalatest._

import dsl._
import dsl.Constraint._
import dsl.RangeVal._
import dsl.SumDsl._
import solver.expressions.IntVar
import solver.expressions.Sum

class SudokuDSLTest extends FlatSpec with Matchers {

  "Sudoku" should "work" in {

    val maxVal = 9
    val checkSum = 45

    var s = SolverDSL
    for (i <- 1 to 9 * 9) {
      "item_" + i -> (0 to maxVal)
    }

    s.E(0 to 72, 9, i => {
      S(i to (i + 8), 1, "item_%") === checkSum
    })

    s.E(0 to 8, 1, i => {
      S(i to (72 + i), 9, "item_%") === checkSum
    })

    if (s.solve) println(s.solution)
    else println("infeasible")
  }

}