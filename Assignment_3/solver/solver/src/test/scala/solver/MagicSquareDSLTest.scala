package solver

import org.scalatest._

import dsl._
import dsl.Constraint._
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
    s.assigned(items, i => {
      "item_%" -> (0 to magicSum)
    })
    s.E(0 to 6, 3, i => {
      s.E(i to 2 + i) === magicSum
    })

    s.E(0 to 8, 4) === magicSum
    s.E(2 to 6, 2) === magicSum

    s.E(0 to 2, 1, i => {
      s.E(i to 6 + i, 3) === magicSum
    })

    //add every square is unique, o yééééé
    s.allVariables !== s.allVariables

    if (s.solve) println(s.solution)
    else println("infeasible")
  }

}