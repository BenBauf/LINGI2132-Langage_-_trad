package dsl

import solver.expressions.IntVar

class RangeVal(n: String, r: Range) extends IntVar(n, r.start, r.end) {

  def to(value: Int): RangeVal = {
    return new RangeVal(this.name, 0 to value)
  }

  def ->(r: Range): RangeVal = {
    return new RangeVal(this.name, r)
  }

  def changeName(value: Int): RangeVal = {
    new RangeVal(this.name.replace("%", "" + value), this.r)
  }

}

object RangeVal {
  def apply(n: String, range: Range) = {
    val r = new RangeVal(n, range)
    r
  }
  implicit def String2RangeVal(value: String) = {
    new RangeVal(value, 0 to 1)
  }
  implicit def IntVar2RangeVal(value: IntVar) = {
    new RangeVal(value.name, value.min to value.max)
  }
}