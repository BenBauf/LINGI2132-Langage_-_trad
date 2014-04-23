package solver.expressions

import solver.core.Assignment

class IntVar(n: String, val min: Int, val max: Int) extends Term {

  /** Returns the name of the variable. */
  val name: String = if (n.isEmpty) "INT_VAR_" + IntVar.count else n
  
  override def toSum: Sum = Sum(this)
  
  override def value(assignment: Assignment): Int = assignment.intMap(this)
  
  override def toString: String = name
}

object IntVar {
  private var count = 0
}