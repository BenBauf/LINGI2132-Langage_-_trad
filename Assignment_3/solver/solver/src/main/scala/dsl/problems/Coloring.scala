package dsl.problems

import dsl._
import dsl.SumDsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum

class Coloring(n: Int, max: Int) extends Problem {
  val nNodes = n
  val Nodes = 0 until nNodes
  var maxColor = max

  val nColors = "nColor" to maxColor

  override def compute() {
    this.isComputed = true
    assigned(n, i => {
      val x = "node" + 1 to maxColor
      x
    })

    addVariable(nColors)

    val rand = new scala.util.Random(0)
    for (n1 <- Nodes; n2 <- Nodes; if n1 < n2 && rand.nextInt(100) < 70) {
      addConstraint {
        val sum = variable(n1) - variable(n2)
        >>(sum + 1) | >>(sum.neg + 1)
      }
    }

    for (n <- Nodes) {
      addConstraint(>>(variable(n) - nColors))
    }
  }

}