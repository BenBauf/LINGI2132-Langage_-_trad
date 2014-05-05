package solver

import org.scalatest._
import dsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.Or
import solver.expressions.LeZero
import solver.expressions.IntVar

class ColoringDSLTest extends FlatSpec with Matchers {

  "Coloring" should "work" in {
    val nNodes = 10
    val Nodes = 0 until nNodes
    var maxColor = 10

    var s = new SolverDSL(nNodes)
    s.assigned(i => {
      val x = "node" + 1 to maxColor
      x
    })

    /*val colors = Array.tabulate(nNodes)(i => {
      val x = new IntVar(s"node $i", 0, maxColor)
      solver.addVariable(x)
      x
    })*/

    val nColors = "nColor" to maxColor
    s.addVariable(nColors)

    val rand = new scala.util.Random(0)
    for (n1 <- Nodes; n2 <- Nodes; if n1 < n2 && rand.nextInt(100) < 70) {
      s.addConstraint {
        val sum = s.variable(n1) - s.variable(n2)
        >>(sum + 1) | >>(sum.neg + 1)
      }
    }

    for (n <- Nodes) {
      s.addConstraint(>>(s.variable(n) - nColors))
    }

    while (s.solveWith(new LeZero(nColors - (maxColor - 1)))) {
      val solution = s.solution
      maxColor = nColors.value(solution)
      println(solution)
    }

    println("finished")
  }

}