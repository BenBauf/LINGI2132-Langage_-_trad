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
    for (i <- 0 until nNodes) {
      "node" + i -> (0 to maxColor)
    }

    "nColor" to maxColor

    val rand = new scala.util.Random(0)
    for (n1 <- Nodes; n2 <- Nodes; if n1 < n2 && rand.nextInt(100) < 70) {
      val sum = s.getItem("node%", n1) - s.getItem("node%", n2)
      (0 >== (sum + 1)) | (0 >== (sum.neg + 1))
    }

    for (n <- Nodes) {
      0 >== (s.getItem("node%", n) - s.getItem("nColor"))
    }

    while (s.solveWith(0 >== s.getItem("nColor") - (maxColor - 1))) {
      val solution = s.solution
      maxColor = s.getItem("nColor").value(solution)
      println(solution)
    }

    println("finished")
  }

  "Coloring encapsulation" should "work" in {
    val nNodes = 10
    val Nodes = 0 until nNodes
    var maxColor = 10

    var c = new Coloring(nNodes, maxColor)
    var s = SolverDSL

    while (c.solveProblemWith((c.nColors - (maxColor - 1)) <== 0)) {
      val solution = c.solution
      maxColor = s.getItem("nColor").value(s.solution)
      println(solution)
    }

    println("finished")
  }

}