package solver.core

import solver.expressions.BooleanVar
import solver.expressions.IntVar
import solver.expressions.Literal

/**
 * `CSP` is a case class representing the CSP (Constraint Satisfaction Problem).
 *
 * It consists of integer variables (`variables`),
 * boolean variables (`bools`),
 * domain function (`dom`), and
 * constraints (`constraints`).
 * Variables of these members are declared by `var`.   Therefore, CSP is a mutable object.
 *
 * {{{
 *     val csp = CSP()                  // Create a new CSP
 *     val x = csp.int(Var("x"), 1, 9)  // declare a variable x in 1..9.
 *     val y = csp.int(Var("y"), 1, 9)  // declare a variable y in 1..9.
 *     csp.add(x + y === 7)             // add a constraint of x === y.
 * }}}
 *
 * Implicit conversion from Scala Symbol to Var is defined in [[jp.kobe_u.scarab.csp]] package object.
 * Therefore, the above program can be written as follows when `jp.kobe_u.scarab.csp._` is imported.
 *
 * {{{
 *     val csp = CSP()
 *     csp.int('x, 1, 9)
 *     csp.int('y, 1, 9)
 *     csp.add('x + 'y === 7)
 * }}}
 *
 * When the CSP is encoded by [[jp.kobe_u.scarab.solver.Encoder]],
 * the contents of the CSP (that is, variables, bools, dom, and constraints) will be modified.
 *
 * @constructor Creates a new `CSP` consisting of specified variables, bools, domain, and constraints.
 * @param variables integer variables of the CSP
 * @param bools Boolean variables of the CSP
 * @param dom domain function of integer variables of the CSP
 * @param constraints constraints of the CSP
 * @see [[jp.kobe_u.scarab]].
 */
class CSP {

  var variables: IndexedSeq[IntVar] = IndexedSeq.empty
  var bools: IndexedSeq[BooleanVar] = IndexedSeq.empty
  var constraints: IndexedSeq[Literal] = IndexedSeq.empty

  var variablesSize = 0
  var boolsSize = 0
  var constraintsSize = 0
  var changed = false
  var rollbackHappen = false

  private var variablesSet: Set[IntVar] = Set.empty
  private var boolsSet: Set[BooleanVar] = Set.empty

  def addVariable(x: IntVar): Unit = {
    if (variablesSet.contains(x)) throw new IllegalArgumentException("the variable already exist")
    else {
      variablesSet += x
      variables = variables :+ x
    }
  }

  /**
   * Adds the Boolean variable `p` to the CSP and returns the variable.
   * It throws IllegalArgumentException when `p` is added twice or more.
   * @param p the Boolean variable to be added
   * @return the Boolean variable added
   * @throws IllegalArgumentException when `p` is added twice or more
   */
  def bool(p: BooleanVar): BooleanVar = {
    if (boolsSet.contains(p)) throw new IllegalArgumentException("duplicate bool " + p)
    boolsSet += p; bools = bools :+ p; p
  }

  /**
   * Adds a new anonymous Boolean variable to the CSP and returns the variable.
   * @return the anonymous Boolean variable added
   */
  def newBool: BooleanVar = bool(new BooleanVar(""))

  /**
   * Adds the constraint `c` to the CSP and returns the constraint.
   * @return the constraint added
   */
  def addConstraint(c: Literal): Unit = {
    constraints = constraints :+ c
    changed = true
  }

  /**
   * Displays the CSP.
   */
  def show {
    for (x <- variables) println(x)
    for (p <- bools)
      println("bool(" + p + ")")
    for (c <- constraints)
      println(c)
  }

  /**
   * for commit/rollback model
   */
  def commit {
    // remember the size of variables
    variablesSize = variables.size

    // remember the size of bools
    boolsSize = bools.size

    // remember the size of constraints
    constraintsSize = constraints.size

  }

  def rollback {
    // rollback variables to the rememberd size of variables
    variables = variables.take(variablesSize)
    // replace variableSet with reduced variables
    variablesSet = variables.toSet

    // rollback bools to the rememberd size of bools
    bools = bools.take(boolsSize)
    // replace boolsSet with reduced bools
    boolsSet = bools.toSet

    // rollback constraints to the rememberd size of constraints
    constraints = constraints.take(constraintsSize)
    rollbackHappen = true
    changed = true
  }
}
