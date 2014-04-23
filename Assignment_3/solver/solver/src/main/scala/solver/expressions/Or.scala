package solver.expressions

import solver.core.Assignment

/**
 * `Or` is a case class representing the disjunction of constraints.
 *
 * @constructor Creates a new `Or` constraint representing the disjunction of the elements of `cs`.
 * @param cs elements of the disjunction
 */
case class Or(cs: Literal*) extends Literal {
  
  override def unary_! : Literal = And(cs.map(!_))
  
  override def value(assignment: Assignment) = cs.exists(_.value(assignment))
  
  override def toString = cs.mkString(productPrefix + "(", ",", ")")
  
  override def toLeZero: Literal = Or(cs.map(_.toLeZero))
}

object Or {
  /** Returns the disjunction of the elements of `cs`. */
  def apply(cs: TraversableOnce[Literal]) = new Or(cs.toSeq: _*)
}