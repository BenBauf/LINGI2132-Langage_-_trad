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
import solver.core.Assignment

class SolverDSL() {

}
object SolverDSL {
  private var s: Solver = new Solver
  private var itemsList: List[IntVar] = Nil
  private var constraint: Set[Constraint] = Set()
  private var variable: Set[RangeVal] = Set()

  private var isAdded = false
  private var count = 0

  private var itemsHash: Map[String, IntVar] = Map()

  def range(): Range = {
    0 until count
  }

  def init() {
    s = new Solver
    isAdded = false
    itemsList = Nil
    count = 0
    itemsHash = Map()
    constraint = Set()
    variable = Set()
  }

  def assign(r: RangeVal, i: Int = count) {
    val x: RangeVal = r.changeName(i)
    //s.addVariable(x)
    itemsList = itemsList.+:(x)
    itemsHash += (x.name -> x)
    variable = variable + x
    count = count + 1
  }

  def assigned(c: Int, body: Int => RangeVal) {
    for (i <- 0 until c) {
      assign(body(i), i)
      body(i).changeName(i)
    }
  }

  def variable(i: Int): IntVar = {
    itemsList(i)
  }

  def solveWith(c: Constraint): Boolean = {
    constraint = constraint - (c)
    addAllConstraintsandvariables
    println(itemsList.size)
    s.solveWith(c.literal)
  }

  def addConstraint(c: Constraint) {
    constraint = constraint + (c)
  }

  def rmConstraint(c: Constraint) {
    constraint = constraint - (c)
  }

  def getItem(name: String): IntVar = {
    return itemsHash.get(name).get
  }

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
    val allV = new Array[Sum](count)
    var s: Sum = (Sum(0))
    for (i <- range) {
      allV(i) = s + variable(i)
    }
    new Chose(allV)
  }

  def solution(): Assignment = {
    s.solution
  }

  def addVariable(x: RangeVal) {
    itemsList = itemsList.+:(x)
    itemsHash += (x.name -> x)
    count = count + 1
    variable = variable + x
    //s.addVariable(x)
  }

  def rmVariable(x: RangeVal) {

  }

  def solve(): Boolean = {
    addAllConstraintsandvariables
    s.solve
  }

  private def addAllConstraintsandvariables() {
    if (isAdded) { return }
    isAdded = true
    for (i <- constraint) {
      s.addConstraint(i.literal)
    }
    for (i <- variable) {
      //println(i)
      s.addVariable(i)
    }
  }
}