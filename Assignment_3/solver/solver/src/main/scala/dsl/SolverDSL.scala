package dsl

import solver.core.SatSolver
import solver.core.encoder.OrderEncoder
import solver.expressions.IntVar
import solver.core.CSP
import solver.Solver
import solver.expressions.LeZero
import solver.expressions.Or
import solver.expressions.Sum
import solver.expressions.And

class SolverDSL(length :Int, profit :Array[Int], weight :Array[Int], cap :Int) {
  
  private val nItems = length
  private val items = 0 until nItems
  
  private val profits = profit
  private val weights = weight
  
  private val solver = new Solver
  
  val assigned =new Array[Int](nItems)
  
  def assigned( body: Int => IntVar){
    for(i<-0 to nItems){
      solver.addVariable(body(i))
    }
  }
  
  def addValues(){
    val p = new IntVar("profit", 0, profits.sum)
    solver.addVariable(p)
  
    val w = new IntVar("weight", 0, weights.sum)
    solver.addVariable(w)
  }
  
  def addConstraint(c : Constraint){
    solver.addConstraint(c.literal)
  }

}