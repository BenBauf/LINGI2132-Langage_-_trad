package dsl

import solver.expressions.IntVar

class RangeVal(n: String, r: Range) extends IntVar(n, r.start, r.end) {

  def range(r: Range): RangeVal = {
    return new RangeVal(this.name, r)
  }

}

object RangeVal {
  def apply(n: String, range: Range) = new RangeVal(n, range)
  implicit def String2RangeVal(value: String) = {
    new RangeVal(value, 0 to 1)
  }
}