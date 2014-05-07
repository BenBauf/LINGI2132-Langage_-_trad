package dsl

import solver.expressions.IntVar

class RangeVal(n: String, r: Range) extends IntVar(n, r.start, r.end) {

  def to(value: Int): RangeVal = {
    SolverDSL.rmVariable(this)
    RangeVal(this.name, 0 to value)
  }

  def ->(r: Range): RangeVal = {
    SolverDSL.rmVariable(this)
    RangeVal(this.name, r)
  }

  def changeName(value: Int): RangeVal = {
    SolverDSL.rmVariable(this)
    RangeVal(this.name.replace("%", "" + value), this.r)
  }

}

object RangeVal {
  def apply(n: String, range: Range) = {
    val r = new RangeVal(n, range)
    SolverDSL.addVariable(r)
    r
  }
  implicit def String2RangeVal(value: String) = {
    val s = SolverDSL
    if (s.exists(value)) {
      s.getItem(value)
    } else
      RangeVal(value, 0 to 1)
  }
  implicit def IntVar2RangeVal(value: IntVar) = {
    RangeVal(value.name, value.min to value.max)
  }
}