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

class SolverDSL(length: Int) extends Solver {

  private val nItems = length
  private val items = 0 until nItems

  private val itemsList = new Array[IntVar](nItems)

  var itemsHash: Map[String, IntVar] = Map()

  def range(): Range = {
    items
  }

  def assigned(body: Int => RangeVal) {
    for (i <- 0 until nItems) {
      val x = body(i).changeName(i)
      //x.changeName(i)
      addVariable(x)
      itemsList(i) = x
      itemsHash += (x.name -> x)
      x
    }
  }

  def variable(i: Int): IntVar = {
    itemsList(i)
  }

  def solveWith(c: Constraint): Boolean = {
    this.solveWith(c.literal)
  }

  def addConstraint(c: Constraint) {
    addConstraint(c.literal)
  }

  def getItem(name: String): IntVar = {
    return itemsHash.get(name).get
  }

  /*def E(range: Range): SumDsl = {
    var s: Sum = (Sum(0))
    for (i <- range) {
      s = s + variable(i)
    }
    return new SumDsl(s)
  }*/

  def E(range: Range, pas: Int = 1): SumDsl = {
    var s: Sum = (Sum(0))
    for (i <- range by pas) {
      s = s + variable(i)
    }
    return new SumDsl(s)
  }

  def E(range: Range, pas: Int, body: Int => Unit) {
    for (i <- range by pas) {
      body(i)
    }
  }

  def allVariables(): Chose = {
    val allV = new Array[Sum](nItems)
    var s: Sum = (Sum(0))
    for (i <- items) {
      allV(i) = s + variable(i)
    }
    new Chose(allV)
  }

}