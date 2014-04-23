package solver.expressions

import solver.core.Assignment

/**
 * `Literal` is an abstract class for literals.
 *
 * {{{
 *     Literal    ::= BooleanVar | Not | LeZero
 * }}}
 */
abstract class Literal extends Expr {
  
    /** Returns the negation of this constraint. */
  def unary_! : Literal 
  
  /** Returns the truth value of the constraint under the given assignment. */
  def value(assignment: Assignment): Boolean
  
  /** Returns this constraint expressed as a conjunction or a disjunction of `LeZero` constraint. */
  def toLeZero: Literal
}