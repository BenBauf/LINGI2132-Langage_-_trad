package solver

import org.scalatest._

import dsl._
import dsl.Constraint._
import dsl.RangeVal._
import dsl.SumDsl._
import solver.expressions.IntVar
import solver.expressions.Sum

class MagicSquareDSLTest extends FlatSpec with Matchers {

  "MagicSquare" should "work" in {
    
    val magicNumber = 3 // number of lines/columns
    
    val magicSum = 15
    val zero = 0
    val possibilities = zero.to(magicSum).toArray
    
    
    var s = new SolverDSL(magicNumber^2);
    s.assigned(i => {
      "item_" + (i + 1)      
    })
    
    
    
    s.addConstraint(
      (s.variable(1)+s.variable(2)+s.variable(3)) === magicSum
     )
     
    s.addConstraint(
      (s.variable(4)+s.variable(5)+s.variable(6)) === magicSum
     )
     
     s.addConstraint(
      (s.variable(7)+s.variable(8)+s.variable(9)) === magicSum
     )
     
     s.addConstraint(
      (s.variable(1)+s.variable(4)+s.variable(7)) === magicSum
     )
     
     s.addConstraint(
      (s.variable(2)+s.variable(5)+s.variable(8)) === magicSum
     )
     
     s.addConstraint(
      (s.variable(3)+s.variable(6)+s.variable(9)) === magicSum
     )
     
     s.addConstraint(
      (s.variable(1)+s.variable(5)+s.variable(9)) === magicSum
     )
     
     s.addConstraint(
      (s.variable(3)+s.variable(5)+s.variable(7)) === magicSum
     )
     
if (s.solve) println(s.solution)
    else println("infeasible")
  }

}