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
    for (i <- 0 until nItems) {
      "item_" + i -> (0 to 1)
    }

    val profitsVar = s.range.map(i => s.getItem("item_%", i) * profits(i))

    val weightsVar = s.range.map(i => s.getItem("item_%", i) * weights(i))

    val totProfit = profitsVar.foldLeft(Sum.zero) {
      (acc, sum) => acc.add(sum)
    }

    val totWeight = weightsVar.foldLeft(Sum.zero) {
      (acc, sum) => acc.add(sum)
    }
    s.addVariable(p)

    val w = "weight" to weights.sum
    s.addVariable(w)

    0 === p - totProfit

    w - totWeight === 0
    0 >= w - capa
  }
}