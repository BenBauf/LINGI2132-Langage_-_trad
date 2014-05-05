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

    val magicSum = 15
    val zero = 0
    val possibilities = zero.to(magicSum).toArray

    var s = new SolverDSL(magicNumber * magicNumber);
    s.assigned(i => {
      //TODO 0 to magicSum
      "item_" + (i + 1) range (0 to magicSum)
    })

    /*s.addConstraint(
      (s.variable(0) + s.variable(1) + s.variable(2)) === magicSum)*/

    s.addConstraint(s.E(0 to 2) === magicSum)
    s.addConstraint(s.E(3 to 5) === magicSum)
    s.addConstraint(s.E(6 to 8) === magicSum)

    /*s.addConstraint(
      (s.variable(3) + s.variable(4) + s.variable(5)) === magicSum)

    s.addConstraint(
      (s.variable(6) + s.variable(7) + s.variable(8)) === magicSum)*/

    s.addConstraint(
      (s.variable(0) + s.variable(4) + s.variable(8)) === magicSum)

    s.addConstraint(
      (s.variable(2) + s.variable(4) + s.variable(6)) === magicSum)

    s.addConstraint(
      (s.variable(0) + s.variable(3) + s.variable(6)) === magicSum)

    s.addConstraint(
      (s.variable(1) + s.variable(4) + s.variable(7)) === magicSum)

    s.addConstraint(
      (s.variable(2) + s.variable(5) + s.variable(8)) === magicSum)

    if (s.solve) println(s.solution)
    else println("infeasible")
  }

}