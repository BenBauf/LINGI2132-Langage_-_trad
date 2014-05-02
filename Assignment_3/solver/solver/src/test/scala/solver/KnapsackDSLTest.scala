package solver

import org.scalatest._
import dsl.SolverDSL
import solver.expressions.IntVar


class KnapsackDSLTest extends FlatSpec with Matchers {

  "Knapsack" should "work" in { 
    
    val nItems = 5

    val profits = Array(2, 5, 1, 3, 4)
    val weights = Array(3, 4, 2, 3, 3)
    val capa = 10
    var s = new SolverDSL(nItems, profits, weights, capa)
    s.assigned(i => {
    	val x = new IntVar("item_" + (i+1), 0, 1)
    	x
    })
    s.addValue()
    
    
  }
  


}