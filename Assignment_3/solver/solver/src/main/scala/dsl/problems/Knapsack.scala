package dsl.problems

import dsl._
import dsl.SumDsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum

class Knapsack(n: Int, profit: Array[Int], weight: Array[Int], ca: Int) extends Problem {
  private val nItems = n

  private val profits = profit
  private val weights = weight
  private val capa = ca

  val p = "profit" to profits.sum

  override def compute() {
    s.init
    this.isComputed = true
    s.assigned(nItems, i => {
      "item_" + (i + 1)
    })

    val profitsVar = s.range.map(i => s.variable(i) * profits(i))

    val weightsVar = s.range.map(i => s.variable(i) * weights(i))

    val totProfit = profitsVar.foldLeft(Sum.zero) {
      (acc, sum) => acc.add(sum)
    }

    val totWeight = weightsVar.foldLeft(Sum.zero) {
      (acc, sum) => acc.add(sum)
    }
    s.addVariable(p)

    val w = "weight" to weights.sum
    s.addVariable(w)

    s.addConstraint(0 === p - totProfit)

    s.addConstraint(w - totWeight === 0)

    s.addConstraint(0 >= w - capa)
  }
}