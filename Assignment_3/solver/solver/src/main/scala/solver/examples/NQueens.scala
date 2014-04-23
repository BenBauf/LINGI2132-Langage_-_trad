package solver.examples

import solver.core.SatSolver
import solver.core.encoder.OrderEncoder
import solver.expressions.IntVar
import solver.core.CSP
import solver.Solver
import solver.expressions.And
import solver.expressions.LeZero
import solver.expressions.Sum
import solver.expressions.Or

object NQueens extends App {
  
  val nQueens = 10
  val queens = 0 until nQueens

  val solver = new Solver

  val columns = queens.map(i => {
    val x = new IntVar("queen " + (i+1), 1 , nQueens)
    solver.addVariable(x)
    x
  })
  
  val upDiags = queens.map(i => columns(i) - i)
  val doDiags = queens.map(i => columns(i) + i)
  
  for (q1 <- queens; q2 <- queens; if q1 < q2) {
    
    solver.addConstraint {
      val sum = columns(q1) - columns(q2) 
      Or(LeZero(sum + 1), LeZero(sum.neg + 1))
    }
    
    solver.addConstraint {
      val sum = upDiags(q1) - upDiags(q2) 
      Or(LeZero(sum + 1), LeZero(sum.neg + 1))
    }
    
    solver.addConstraint {
      val sum = doDiags(q1) - doDiags(q2) 
      Or(LeZero(sum + 1), LeZero(sum.neg + 1))
    }
  }
  
  if (solver.solve) println(solver.solution)
  else println("infeasible")
}