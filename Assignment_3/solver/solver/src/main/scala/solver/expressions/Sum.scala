package solver.expressions

import solver.core.Assignment

/**
 * `Sum` is a class for linear expressions. A `Sum` object consists of an integer 
 * constant `b` and a mapping `coef` representing the coefficients of integer variables.
 * A `Sum` represents the linear summation b + a1*x1 + a2*x2 + ... + an*xn.
 *
 * Addition, subtraction, negation, and constant multiplication are defined on `Sum` objects.
 * When a coefficient becomes zero, it is removed from `coef`.
 * {{{
 *     Sum(x).add(1)         // 1 + x
 *     Sum(x).add(1).sub(x)  // 1
 *     Sum(x).mul(3)         // 3 * x
 * }}}
 *
 * Infix and prefix operators defined in `Term` can be used.
 * {{{
 *     x + 1                 // 1 + 1 * x
 *     x + 1 - x             // 1
 *     x * 3                 // 0 + 3 * x
 * }}}
 * 
 * The companion object `Sum` provides several easy-to-use construction methods for `Sum`.
 * {{{
 *     Sum(1)                // 1
 *     Sum(x(1), x(2))       // x(1) + x(2)
 *     Sum(x(1), x(1))       // 2 * x(1)
 * }}}
 *
 * @constructor Creates a new `Sum` of the given constant and variables with specified coefficients.
 * @param b constant part of the summation
 * @param coef map consisting of variables and their coefficients
 */
class Sum(val b: Int, val coef: Map[IntVar, Int]) extends Term {
  
  require(coef.values.forall(_ != 0), "Coefficients of Sum should not be zero.")
  
  override def toSum: Sum = this
  
  /** Returns the negation of this, that is, `-this`. */
  def neg: Sum = new Sum(-b, coef.map { case (x, a) => (x, -a) })
  
  /** Returns the addition of this and `b`. */
  def add(b1: Int): Sum = new Sum(b + b1, coef)
  
  /** Returns the addition of this and `a*x`.  When `a` is omitted 1 is used. */
  def add(x: IntVar, a: Int = 1): Sum = {
    val a1 = coef.getOrElse(x, 0) + a
    val coef1 = if (a1 == 0) coef - x else coef + (x -> a1)
    new Sum(b, coef1)
  }
  
  /** Returns the addition of this and `that`. */
  def add(that: Sum): Sum = {
    that.coef.foldLeft(this)((sum, xa) => sum.add(xa._1, xa._2)).add(that.b)
  }
    
  /** Returns the subtraction of `b` from this. */
  def sub(b1: Int): Sum = add(-b1)
  
  /** Returns the subtraction of `a*x` from this.  When `a` is omitted 1 is used. */
  def sub(x: IntVar, a: Int = 1): Sum = add(x, -a)
  
  /** Returns the subtraction of `that` from this. */
  def sub(that: Sum): Sum = add(that.neg)
  
  /** Returns the multiplication of this by `a`. */
  def mul(a: Int): Sum = {
    if (a == 0) Sum.zero 
    else new Sum(a * b, coef.map(xa => (xa._1, a * xa._2)))
  }
    
  def value(assignment: Assignment): Int = {
    b + coef.map(xa => xa._1.value(assignment) * xa._2).sum
  }
  
  override def toString = {
    def s(a: Int, x: IntVar) =
      if (a == 0) ""
      else if (a < 0) "-" + (if (a == -1) "" else -a + "*") + x.toString
      else "+" + (if (a == 1) "" else a + "*") + x.toString
    ((if (b == 0) "" else b.toString) +: coef.map(xa => s(xa._2, xa._1)).toSeq).mkString("Sum(", "", ")")
  }
}

object Sum {
  
  /** Constant value 0. */
  val zero = new Sum(0, Map.empty)
  
  /** Returns the sum of `b` and variables `xs`. */
  def apply(b: Int, xs: IntVar*): Sum = xs.foldLeft(zero.add(b))(_.add(_))
  
  /** Returns the sum of `x` and variables `xs`. */
  def apply(x: IntVar, xs: IntVar*): Sum = (x +: xs).foldLeft(zero)(_.add(_))
  
  /** Returns the sum of the elements of `xs`. */
  def apply(xs: Seq[IntVar]): Sum = xs.foldLeft(zero)(_.add(_))
}