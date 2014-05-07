package solver

import org.scalatest._
import dsl._
import dsl.SumDsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum
import dsl.problems.Coloring

class ExpressionTest extends FlatSpec with Matchers {

  "expression" should "work" in {

    var s = new SolverDSL()
    s.assigned("node%" -> (0 to 2))
    s.assigned("node%" -> (7 to 9))

    s.addConstraint(s.variable(0) === s.variable(1))

    if (s.solve) println(s.solution)
    else println("infeasible")

  }

}