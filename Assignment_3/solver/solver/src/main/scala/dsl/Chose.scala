package dsl

import solver.expressions.Sum
import dsl.SumDsl._

class Chose(t: Array[Sum]) {
  private val tab = t

  def ===(t: Chose): List[Constraint] = {
    val allC = List()
    for (i <- tab) {
      for (j <- t.tab) {
        if (!i.toString.equals(j.toString)) {
          allC.+:(i === j)
        }
      }
    }
    return allC
  }

  def !==(t: Chose): List[Constraint] = {
    val allC = List()
    for (i <- tab) {
      for (j <- t.tab) {
        if (!i.toString.equals(j.toString)) {
          allC.+:(i !== j)
        }
      }
    }
    return allC
  }

}