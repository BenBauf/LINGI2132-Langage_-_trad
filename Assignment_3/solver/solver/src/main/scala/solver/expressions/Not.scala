package solver.expressions

import solver.core.Assignment

/**
 * `Not` is a case class for negations of Boolean variables.
 *
 * @constructor Creates a new `Not` of the given Boolean variable.
 * @param p Boolean variable
 */
case class Not(p: BooleanVar) extends Literal {
  
  override def unary_! : Literal = p
  
  def value(assignment: Assignment) = !p.value(assignment)
  
  override def toLeZero: Literal = this
}