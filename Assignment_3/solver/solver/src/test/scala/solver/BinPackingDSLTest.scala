package solver

import org.scalatest._
import dsl._
import dsl.Constraint._
import dsl.RangeVal._
import dsl.SumDsl._
import solver.expressions.IntVar
import solver.expressions.Sum
import dsl.problems.Knapsack

class BinPackingDSLTest extends FlatSpec with Matchers {

  "BinPacking" should "work" in {

    val binHeight = 20
    val binDivision = 3
    val weights = Array(5, 8, 3, 3, 4, 4, 2, 9, 4, 6, 7, 4)
    val profits = Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    val pieces = weights.length

    val knArray = new Array[Knapsack](binDivision)
    for (i <- 1 to binDivision) {
      knArray(i - 1) = new Knapsack(pieces, profits, weights, binHeight)
    }

    var s = new SolverDSL();
    /*s.assigned(binDivision, i => {
      "item_%" knArray (i)
    })*/

    /*  val s = new SolverDSL(pieces)
    s.assigned(i => {
      "item_" + (i + 1) to 2
    })
    
    var su = Sum(0)
    
    if(s.variable(0) == 1)
      su= su +weights(0)
    else
        ???
        
    s.addConstraint(su <= binHeight)*/

    /*val profitsVar = s.range.map(i => s.variable(i) * profits(i))

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

    s.addConstraint(0 === p - totProfit)

    s.addConstraint(w - totWeight === 0)

    s.addConstraint(0 >= w - capa)

    var best = 0
    println("on commence")
    while (s.solveWith(0 >= -p + best + 1)) {
      val solution = s.solution
      best = p.value(solution)
      println(solution)
    }
    println("finished")
    best should be(12)*/

  }
}