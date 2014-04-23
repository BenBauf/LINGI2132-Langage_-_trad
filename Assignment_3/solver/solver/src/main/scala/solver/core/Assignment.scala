package solver.core

import solver.expressions.Term
import solver.expressions.BooleanVar
import solver.expressions.IntVar
import solver.expressions.Literal

/**
 * `Assignment` is a class representing the assignment for integer variables and Boolean variables.
 * The assignment is returned from the `Solver` as a solution.
 *
 * @constructor Creates a new assignment consisting of `intMap` and `boolMap`.
 * @param intMap the assignment for integer variables
 * @param boolMap the assignment for Boolean variables
 */
class Assignment(val intMap: Map[IntVar, Int], val boolMap: Map[BooleanVar, Boolean]) {

  /** Returns the value of the given term under this assignment. */
  def apply(x: Term): Int = x.value(this)

  /** Returns the truth value of the given constraint under this assignment. */
  def apply(c: Literal): Boolean = c.value(this)

  override def toString: String = intMap.mkString(", ")
}