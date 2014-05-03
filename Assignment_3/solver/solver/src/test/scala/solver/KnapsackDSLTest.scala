package solver

import org.scalatest._
import dsl._
import dsl.RangeVal._
import dsl.Constraint._
import solver.expressions.LeZero
import solver.expressions.IntVar
import solver.expressions.Sum

class KnapsackDSLTest extends FlatSpec with Matchers {

  "Knapsack" should "work" in {

    val nItems = 5

    val profits = Array(2, 5, 1, 3, 4)
    val weights = Array(3, 4, 2, 3, 3)
    val capa = 10

    var s = new SolverDSL(nItems)
    s.assigned(i => {
      val x = "item_" + (i + 1) //new IntVar("item_" + (i + 1), 0, 1)
      x
    })
    //todo add
    val items = 0 until nItems

    val profitsVar = items.map(i => s.assigned(i) * profits(i))

    val weightsVar = items.map(i => s.assigned(i) * weights(i))

    val totProfit = profitsVar.foldLeft(Sum.zero) {
      (acc, sum) => acc.add(sum)
    }

    val totWeight = weightsVar.foldLeft(Sum.zero) {
      (acc, sum) => acc.add(sum)
    }

    //val p = new IntVar("profit", 0, profits.sum)
    val p = ("profit" range (0 to profits.sum)) toIntVar ()
    //s.addVariable(p)
    s.addVariable(p)

    val w = new IntVar("weight", 0, weights.sum)
    s.addVariable(w)

    s.addConstraint {
      >>(p - totProfit) & >>(totProfit - p)
    }

    s.addConstraint {
      >>(w - totWeight) & >>(totWeight - w)
    }

    s.addConstraint {
      >>(w - capa)
    }

    var best = 0
    println("on commence")
    while (s.solveWith(>>(-p + (best + 1)))) {
      val solution = s.solution
      best = p.value(solution)
      println(solution)
    }
    println("finished")
    best should be(12)

  }

}