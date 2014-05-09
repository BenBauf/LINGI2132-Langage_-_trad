package solver

import org.scalatest._
import dsl.ArrayConstraint
import dsl._
import dsl.SumDsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum
import dsl.problems.Coloring

import dsl._
import dsl.Constraint._
import dsl.S
import dsl.S._
import dsl.RangeVal._
import dsl.SumDsl._
import solver.expressions.IntVar
import solver.expressions.Sum

class ExpressionTest extends FlatSpec with Matchers {

  "expression" should "work" in {

    var s = SolverDSL
    "node0" to 2
    "node1" -> (7 to 9)

    String2RangeVal("node0") equal "node1"

    if (s.solve) println(s.solution)
    else println("infeasible")

  }

}