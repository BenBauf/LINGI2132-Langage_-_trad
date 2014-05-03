package solver

import org.scalatest._
import dsl._
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
    /*s.assigned(i => {
      val x = new IntVar("item_" + (i + 1), 0, 1)
      x
    })*/
    //todo add
    val items = 0 until nItems

    val assigned = Array.tabulate(nItems)(i => {
      val x = new IntVar("item_" + (i + 1), 0, 1)
      s.addVariable(x)
      x
    })

    val profitsVar = items.map(i => assigned(i) * profits(i))

    val weightsVar = items.map(i => assigned(i) * weights(i))

    val totProfit = profitsVar.foldLeft(Sum.zero) {
      (acc, sum) => acc.add(sum)
    }

    val totWeight = weightsVar.foldLeft(Sum.zero) {
      (acc, sum) => acc.add(sum)
    }

    val p = new IntVar("profit", 0, profits.sum)
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