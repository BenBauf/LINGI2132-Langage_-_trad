package dsl

import solver.expressions.Sum

class S(su: Sum) {
  var s: Sum = su
}

object S {

  def apply(range: Range, pas: Int = 1)(implicit name: String) = {
    var s: Sum = (Sum(0))
    for (i <- range by pas) {
      s = s + SolverDSL.getItem(i)(name)
    }
    new S(s)
  }

  implicit def S2SumDsl(value: S) = {
    new SumDsl(value.s)
  }

}