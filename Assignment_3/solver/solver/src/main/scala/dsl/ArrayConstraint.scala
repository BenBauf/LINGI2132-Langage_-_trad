package dsl

import solver.expressions.Sum
import dsl.SumDsl._

class ArrayConstraint(t: Array[Sum]) {
  private val tab = t

  def ===(t: ArrayConstraint): List[Constraint] = {
    val allC = List()
    for (i <- tab) yield {
      for (j <- t.tab) yield {
        if (!i.toString.equals(j.toString)) {
          allC.+:(i equal j)
        }
      }
    }
    return allC
  }

  def !==(t: ArrayConstraint): List[Constraint] = {
    val allC = List()
    for (i <- tab) yield {
      for (j <- t.tab) yield {
        if (!i.toString.equals(j.toString)) {
          allC.+:(i dif j)
        }
      }
    }
    return allC
  }

}