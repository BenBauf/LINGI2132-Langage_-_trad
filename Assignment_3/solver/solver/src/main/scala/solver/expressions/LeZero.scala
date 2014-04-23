package solver.expressions

import solver.core.Assignment

/**
 * `LeZero` is a case class representing the constraint meaning the given linear
 * summation is less than or equal to zero, that is, `sum <= 0`.
 *
 * @constructor Creates a new `LeZero` constraint meaning the given linear summation is less than or equal to zero.
 * @param sum linear summation
 */
case class LeZero(sum: Sum) extends Literal {

  override def unary_! : Literal = LeZero(sum.neg.add(1))

  override def value(assignment: Assignment) = sum.value(assignment) <= 0

  override def toLeZero: Literal = this
}