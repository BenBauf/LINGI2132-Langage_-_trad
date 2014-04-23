package solver.examples

import solver.core.SatSolver
import solver.core.encoder.OrderEncoder
import solver.expressions.IntVar
import solver.core.CSP
import solver.Solver
import solver.expressions.LeZero
import solver.expressions.Or

object Coloring extends App {

  val nNodes = 10
  val Nodes = 0 until nNodes
  var maxColor = 10

  val solver = new Solver

  val colors = Array.tabulate(nNodes)(i => {
    val x = new IntVar(s"node $i", 0, maxColor)
    solver.addVariable(x)
    x
  })

  val nColors = new IntVar("nColor", 0, maxColor)
  solver.addVariable(nColors)

  val rand = new scala.util.Random(0)
  for (n1 <- Nodes; n2 <- Nodes; if n1 < n2 && rand.nextInt(100) < 70) {
    solver.addConstraint {
      val sum = colors(n1) - colors(n2)
      Or(LeZero(sum + 1), LeZero(sum.neg + 1))
    }
  }

  for (n <- Nodes) {
    solver.addConstraint(new LeZero(colors(n) - nColors))
  }

  while (solver.solveWith(new LeZero(nColors - (maxColor - 1)))) {
    val solution = solver.solution
    maxColor = nColors.value(solution)
    println(solution)
  }

  println("finished")
}