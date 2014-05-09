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
  private var constraint: Set[Constraint] = Set()
  private var variables: Set[RangeVal] = Set()

  private var isAdded = false
  private var count = 0

  private var itemsHash: Map[String, RangeVal] = Map()

  def range(): Range = {
    0 until count
  }

  def init() {
    s = new Solver
    isAdded = false
    count = 0
    itemsHash = Map()
    constraint = Set()
    variables = Set()
  }

  def assign(r: RangeVal, i: Int = count) {
    val x: RangeVal = r.changeName(i)
    itemsHash += (x.name -> x)
    variables = variables + x
    count = count + 1
  }

  def assigned(c: Int, body: Int => RangeVal) {
    for (i <- 0 until c) {
      assign(body(i), i)
      body(i).changeName(i)
      rmVariable(body(i).changeName(i))
    }
  }

  def solveWith(c: Constraint): Boolean = {
    constraint = constraint - (c)
    addAllConstraintsandvariables
    s.solveWith(c.literal)
  }

  def addConstraint(c: Constraint) {
    constraint = constraint + (c)
  }

  def rmConstraint(c: Constraint) {
    constraint = constraint - (c)
  }

  def getItem(name: String): RangeVal = {
    return itemsHash.get(name).get
  }

  def getItem(i: Int)(implicit name: String): RangeVal = {
    getItem(name.replace("%", "" + i))
  }

  def exists(name: String): Boolean = {
    itemsHash.contains(name)
  }

  def E(range: Range, pas: Int, body: Int => Unit) {
    for (i <- range by pas) {
      body(i)
    }
  }

  def allVariables(): ArrayConstraint = {
    var allV: IndexedSeq[Sum] = IndexedSeq.empty[Sum]
    var s: Sum = (Sum(0))
    var index = 0
    for (i <- variables) {
      allV = allV :+ s + i
      index += 1
    }
    new ArrayConstraint(allV: _*)
  }

  def getSetVariable(r: Array[Int])(implicit name: String): ArrayConstraint = {
    var allV: IndexedSeq[Sum] = IndexedSeq.empty[Sum]
    var s: Sum = (Sum(0))
    var index = 0
    for (i <- r) {
      allV = allV :+ s + getItem(i)(name)

      index += 1
    }
    new ArrayConstraint(allV: _*)
  }
  def getSetVariables(r: Range, pas: Int = 1)(implicit name: String): ArrayConstraint = {
    var allV: IndexedSeq[Sum] = IndexedSeq.empty[Sum]
    var s: Sum = (Sum(0))
    var index = 0
    for (i <- r by pas) {
      allV = allV :+ s + getItem(i)(name)

      index += 1
    }
    new ArrayConstraint(allV: _*)
  }

  def solution(): Assignment = {
    s.solution
  }

  def addVariable(x: RangeVal) {
    itemsHash += (x.name -> x)
    count = count + 1
    variables = variables + x
  }

  def rmVariable(x: RangeVal) {
    itemsHash = itemsHash - x.name
    count = count - 1
    variables = variables - x
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
    for (i <- variables) {
      s.addVariable(i)
    }
  }
}