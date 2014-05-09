package dsl

import solver.expressions.Sum
import dsl.SumDsl._
import dsl.RangeVal._

class ArrayConstraint(t: Sum*) {
  private val tab = t

  def equal(t: ArrayConstraint): List[Constraint] = {
    val allC = List()
    for (i <- tab) {
      for (j <- t.tab) {
        if (!i.toString.equals(j.toString)) {
          allC.+:(i equal j)
        }
      }
    }
    return allC
  }

  def equal(t: SumDsl): List[Constraint] = {
    val allC = List()
    for (i <- tab) {
      allC.+:(i equal t.sum)
    }
    return allC
  }

  def !==(t: ArrayConstraint): List[Constraint] = {
    val allC = List()
    for (i <- tab) {
      for (j <- t.tab) {
        if (!i.toString.equals(j.toString)) {
          allC.+:(i dif j)
        }
      }
    }
    return allC
  }

}