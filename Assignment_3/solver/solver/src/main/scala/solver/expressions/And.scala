package solver.expressions

import solver.core.Assignment

/**
 * `And` is a case class representing the conjunction of constraints.
 *
 * @constructor Creates a new `And` constraint representing the conjunction of the elements of `cs`.
 * @param cs elements of the conjunction
 */
case class And(cs: Literal*) extends Literal {
  
  override def unary_! : Literal = Or(cs.map(!_))
  
  override def value(assignment: Assignment) = cs.forall(_.value(assignment))
  
  override def toString = cs.mkString(productPrefix + "(", ",", ")")
  
  override def toLeZero: Literal = And(cs.map(_.toLeZero))
}

object And {
  /** Returns the conjunction of the elements of `cs`. */
  def apply(cs: TraversableOnce[Literal]) = new And(cs.toSeq: _*)
}