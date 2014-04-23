package solver.core.encoder

import solver.expressions.And
import solver.expressions.BooleanVar
import solver.expressions.Not
import solver.expressions.LeZero
import solver.expressions.Or
import solver.expressions.Literal

class Simplifier(val encoder: OrderEncoder) {
  def flattenOr(c: Literal): Seq[Literal] = c match {
    case Or(cs @ _*) => cs.flatMap(flattenOr(_))
    case _: And => Seq(c)
    case _ => Seq(c)
  }

  def isSimpleLiteral(lit: Literal): Boolean = lit match {
    case _: BooleanVar => true
    case _: Not => true
    case LeZero(sum) => sum.coef.size <= 1
  }

  def isSimpleClause(lits: Seq[Literal]): Boolean =
    lits.count(!isSimpleLiteral(_)) <= 1

  def tseitin(c: Literal): (Literal, Seq[Literal]) = c match {
    case And(cs @ _*) => {
      val p = encoder.newBool
      (p, cs.map(Or(Not(p), _)))
    }
    case Or(cs @ _*) => {
      val p = encoder.newBool
      (p, Seq(Or(Not(p) +: cs)))
    }
    case lit: Literal => (lit, Seq.empty)
  }

  def toCNF(c: Literal): Seq[Seq[Literal]] = c match {
    case And(cs @ _*) => cs.flatMap(toCNF(_))
    case _: Or => {
      val cs = flattenOr(c)
      val ts = cs.map(tseitin(_))
      val clause = ts.map(_._1)
      clause +: ts.flatMap(_._2).flatMap(toCNF(_))
    }  
    case lit: Literal => Seq(Seq(lit))
  }

  def simplify(c: Literal): Seq[Seq[Literal]] =
    toCNF(c).flatMap { lits =>
      if (isSimpleClause(lits))
        Seq(lits)
      else {
        val ts = lits.map { lit =>
          if (isSimpleLiteral(lit))
            (lit, None)
          else {
            val p = encoder.newBool
            (p, Some(Seq(Not(p), lit)))
          }
        }
        val clause = ts.map(_._1)
        clause +: ts.flatMap(_._2)
      }
    }
}
