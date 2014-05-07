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
    println("compute")

    var s = SolverDSL
    s.init
    for (i <- 0 until nNodes) {
      "node" + i -> (0 to maxColor)
    }

    s.addVariable(nColors)

    val rand = new scala.util.Random(0)
    for (n1 <- Nodes; n2 <- Nodes; if n1 < n2 && rand.nextInt(100) < 70) {
      val sum = s.getItem("node%", n1) - s.getItem("node%", n2)
      (0 >== (sum + 1)) | (0 >== (sum.neg + 1))
    }

    for (n <- Nodes) {
      0 >== (s.getItem("node%", n) - s.getItem("nColor"))
    }
    println("toadd")
  }

}