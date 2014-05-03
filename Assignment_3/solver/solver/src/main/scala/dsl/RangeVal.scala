package dsl

import solver.expressions.IntVar

class RangeVal(n: String, r: Range) {

  private val name = n
  private val rangeR = r

  def toIntVar(): IntVar = {
    println(name);
    return new IntVar(name, rangeR.start, rangeR.end)
  }

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