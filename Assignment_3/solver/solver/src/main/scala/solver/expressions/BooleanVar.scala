package solver.expressions

import solver.core.Assignment

class BooleanVar(n: String) extends Literal {
  
  val name: String = if (n.isEmpty) "BOOLEAN_VAR_" + BooleanVar.count else n
  
  override def unary_! : Literal = Not(this)
  
  override def value(assignment: Assignment): Boolean = assignment.boolMap(this)
  
  override def toString: String = name
  
  override def toLeZero: Literal = this
}

object BooleanVar {
  private var count = 0
}