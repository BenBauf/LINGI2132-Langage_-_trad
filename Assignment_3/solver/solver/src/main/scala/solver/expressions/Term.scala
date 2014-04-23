package solver.expressions

import solver.core.Assignment



/**
 * `Term` is an abstract class for linear arithmetic expressions.
 *
 * {{{
 *     Term ::= IntVar | Sum
 * }}}
 */
abstract class Term extends Expr {
  
  /**
   * Returns the `Sum` representation of the term.
   * It returns a new `Sum` object when the term is a variable, 
   * and return itself when the term is a `Sum`.
   */
  def toSum: Sum
  
  /** Returns the negation of the term. */
  def unary_- : Sum = toSum.neg
  
  /** Returns the addition of the term and `b`. */
  def +(b: Int): Sum = toSum.add(b)
  
  /** Returns the addition of the term and `x`. */
  def +(x: Term): Sum = toSum.add(x.toSum)
  
  /** Returns the subtraction of `b` from the term. */
  def -(b: Int): Sum = toSum.sub(b)
  
  /** Returns the subtraction of `x` from the term. */
  def -(x: Term): Sum = toSum.sub(x.toSum)
  
  /** Returns the multiplication of the term and `a`. */
  def *(a: Int): Sum = toSum.mul(a)
  
  /** Returns the value of the term under the given assignment. */
  def value(assignment: Assignment): Int
}