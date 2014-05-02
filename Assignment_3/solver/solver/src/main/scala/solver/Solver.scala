package solver

import solver.expressions.BooleanVar
import solver.expressions.And
import solver.expressions.Not
import solver.expressions.LeZero
import solver.expressions.Literal
import solver.core.SatSolver
import solver.core.CSP
import solver.core.Assignment
import solver.core.encoder.OrderEncoder
import solver.expressions.IntVar

/**
 * `Solver` is a class for CSP solvers.
 * It encodes the given CSP to a SAT instance by using the specified encoder,
 * and solves the encoded SAT instance by using the specified SAT solver.
 *
 * Typical usage of the solver is as follows.
 * {{{
 *     val solver = new Solver(csp, sat, encoder)
 *     if (solver.find) {
 *       println(solver.solution)
 *     }
 * }}}
 *
 * @constructor Creates a new solver
 * @param csp the CSP to be solved
 * @param satSolver the SAT solver to be used
 * @param encoder the encoder to be used
 */
class Solver {
  
  private val csp = new CSP()
  private val satSolver = new SatSolver()
  private val encoder = new OrderEncoder(csp, satSolver)
  private var sol: Assignment = null
  
  def addVariable(x: IntVar): Unit = csp.addVariable(x)
  
  def addConstraint(c: Literal): Unit = csp.addConstraint(c) 

  /**
   *  Returns the solution found by the `solve` method.
   *  Throws a NullPointerException if there is no solution.
   */
  def solution: Assignment = {
    if (sol != null) sol
    else throw new NullPointerException("no solution")
  }

  /** Finds a solution of the given CSP. */
  def solve(): Boolean = {
    encoder.encodeCSP
    csp.changed = false
    val result = satSolver.isSatisfiable
    sol = if (result) encoder.decode else null
    result
  }

  /**
   * With multiple assumptions, finds a solution (assignment) of the
   * given CSP.
   */
  def solveWith(constraint: Literal, constraints: Literal*): Boolean = {
    encoder.encodeCSP
    val result = satSolver.isSatisfiable(convert(Seq(constraint) ++ constraints.toSeq))
    sol = if (result) encoder.decode else null
    result
  }

  /** Sets the time limit of the `solve` and `solveWith` methods. */
  def timeLimit(time: Int): Unit = satSolver.setTimeout(time)

  /** Decompose assumption into literals */
  private def convert(cons: Seq[Literal]): Seq[Int] = {
    def toSat(c: Literal): Seq[Seq[Int]] = {
      c match {
        case p: BooleanVar => encoder.encode(Seq(p))
        case Not(p) => encoder.encode(Seq(Not(p)))
        case LeZero(sum) => encoder.encode(Seq(LeZero(sum)))
        case And(LeZero(sum1), LeZero(sum2)) => {
          var llits = encoder.encode(Seq(LeZero(sum1)))
          llits ++ encoder.encode(Seq(LeZero(sum2)))
        }
        case _ => {
          throw new IllegalArgumentException("un-supported assumption form")
          Seq.empty
        }
      }
    }
    def toAssumps(llits: Seq[Seq[Int]]): Seq[Int] = {
      llits match {
        case Seq(Seq(x)) => Seq(x)
        case Seq(Seq(x), Seq(y)) => Seq(x, y)
        case _ => {
          throw new IllegalArgumentException("un-supported assumption form")
          Seq.empty
        }
      }
    }
    for {
      c <- cons
      lit <- toAssumps(toSat(c)) if lit != encoder.TrueLit
    } yield lit
  }
}
