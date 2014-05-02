package solver.examples


import solver.core.SatSolver
import solver.core.encoder.OrderEncoder
import solver.expressions.IntVar
import solver.core.CSP
import solver.Solver
import solver.expressions.LeZero
import solver.expressions.Or
import solver.expressions.Sum
import solver.expressions.And
import dsl.SolverDSL

object Knapsack extends App {

  val nItems = 5
  val items = 0 until nItems

  val profits = Array(2, 5, 1, 3, 4)
  val weights = Array(3, 4, 2, 3, 3)
  val capa = 10

  val solver = new Solver

  val assigned = Array.tabulate(nItems)(i => {
    val x = new IntVar("item_" + (i+1), 0, 1)
    solver.addVariable(x)
    x
  })

  val profitsVar = items.map(i => assigned(i) * profits(i))

  val weightsVar = items.map(i => assigned(i) * weights(i))

  val totProfit = profitsVar.foldLeft(Sum.zero) {
    (acc, sum) => acc.add(sum)
  }

  val totWeight = weightsVar.foldLeft(Sum.zero) {
    (acc, sum) => acc.add(sum)
  }

  val p = new IntVar("profit", 0, profits.sum)
  solver.addVariable(p)
  
  val w = new IntVar("weight", 0, weights.sum)
  solver.addVariable(w)
  
  solver.addConstraint {
    And(LeZero(p - totProfit), LeZero(totProfit - p))
  }
   
  solver.addConstraint {
    And(LeZero(w - totWeight), LeZero(totWeight - w))
  }
  
  solver.addConstraint {
    LeZero(w - capa)
  }
  
  var best = 0

  while (solver.solveWith(new LeZero(-p + (best + 1)))) {
    val solution = solver.solution
    best = p.value(solution)
    println(solution)
  }

  println("finished")
}