package solver

import org.scalatest._
import dsl._
import dsl.SumDsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum
import dsl.problems.Knapsack

class KnapsackDSLTest extends FlatSpec with Matchers {

  "Knapsack" should "work" in {

    val nItems = 5

    val profits = Array(2, 5, 1, 3, 4)
    val weights = Array(3, 4, 2, 3, 3)
    val capa = 10

    val s = SolverDSL
    s.init
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

    val p = "profit" to profits.sum
    s.addVariable(p)

    val w = "weight" to weights.sum
    s.addVariable(w)

    0 === p - totProfit

    w - totWeight === 0
    0 >= w - capa

    var best = 0
    println("on commence")
    while (s.solveWith(0 >= -p + best + 1)) {
      val solution = s.solution
      best = p.value(solution)
      println(solution)
    }
    println("finished")
    best should be(12)

  }

  "Knapsack encapsulation" should "work" in {

    val nItems = 5

    val profits = Array(2, 5, 1, 3, 4)
    val weights = Array(3, 4, 2, 3, 3)
    val capa = 10

    val s = new Knapsack(nItems, profits, weights, capa)

    var best = 0
    println("on commence")
    while (s.solveProblemWith(0 >= -s.p + best + 1)) {
      val solution = s.solution
      best = s.p.value(solution)
      println(solution)
    }
    println("finished")
    best should be(12)

  }

}