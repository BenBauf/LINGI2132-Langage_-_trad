package solver

import org.scalatest._

import dsl._
import dsl.Constraint._
import dsl.S
import dsl.S._
import dsl.RangeVal._
import dsl.SumDsl._
import solver.expressions.IntVar
import solver.expressions.Sum

class MagicSquareDSLTest extends FlatSpec with Matchers {

  "MagicSquare" should "work" in {

    val magicNumber = 3 // number of lines/columns
    val items = magicNumber * magicNumber
    val magicSum = 15
    val zero = 0
    val possibilities = zero.to(magicSum).toArray

    var s = SolverDSL

    for (i <- 0 until items) {
      "item_" + i -> (0 to magicSum)
    }
    s.E(0 to 6, 3, i => {
      S(i to (2 + i), 1, "item_%") equal magicSum
    })

    S(0 to 8, 4, "item_%") === magicSum
    S(2 to 6, 2, "item_%") === magicSum

    s.E(0 to 2, 1, i => {
      S(i to 6 + i, 3, "item_%") === magicSum
    })

    //add every square is unique, o yééééé
    s.allVariables !== s.allVariables

    if (s.solve) println(s.solution)
    else println("infeasible")
  }

}