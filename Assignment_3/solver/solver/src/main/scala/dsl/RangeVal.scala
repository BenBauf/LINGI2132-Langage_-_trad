package dsl

import solver.expressions.IntVar

class RangeVal(n: String, r: Range) extends IntVar(n, r.start, r.end) {

  def to(value: Int): RangeVal = {
    return new RangeVal(this.name, 0 to value)
  }

  def ->(r: Range): RangeVal = {
    return new RangeVal(this.name, r)
  }

  def ->(value: Int): RangeVal = to(value)

  def changeName(value: Int): RangeVal = {
    new RangeVal(this.name.replace("%", "" + value), this.r)
  }

}

object RangeVal {
  def apply(n: String, range: Range) = new RangeVal(n, range)
  implicit def String2RangeVal(value: String) = {
    new RangeVal(value, 0 to 1)
  }
}