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

class ColoringDSLTest extends FlatSpec with Matchers {

  "Coloring" should "work" in {
    val nNodes = 10
    val Nodes = 0 until nNodes
    var maxColor = 10

    var s = SolverDSL
    s.assigned(nNodes, i => {
      val x = "node%" to maxColor
      x
    })

    val nColors = "nColor" to maxColor
    s.addVariable(nColors)

    val rand = new scala.util.Random(0)
    for (n1 <- Nodes; n2 <- Nodes; if n1 < n2 && rand.nextInt(100) < 70) {
      val sum = s.variable(n1) - s.variable(n2)
      0 >= (sum + 1) | 0 >= (sum.neg + 1)
    }

    for (n <- Nodes) {
      0 >= (s.variable(n) - nColors)
    }

    while (s.solveWith(0 >= nColors - (maxColor - 1))) {
      val solution = s.solution
      maxColor = nColors.value(solution)
      println(solution)
    }

    println("finished")
  }

  "Coloring encapsulation" should "work" in {
    val nNodes = 10
    val Nodes = 0 until nNodes
    var maxColor = 10

    var s = new Coloring(nNodes, maxColor)

    while (s.solveProblemWith((s.nColors - (maxColor - 1)) <= 0)) {
      val solution = s.solution
      maxColor = s.nColors.value(s.solution)
      println(solution)
    }

    println("finished")
  }

}