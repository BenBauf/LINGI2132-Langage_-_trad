package polynom

import org.scalatest._
import polynom._
import polynom.Polynom._

class PolynomSuite extends FlatSpec with Matchers {

  "Polynom" should "be able to add" in {
    
    
    //TODO: uncomment and make it work
    val y: Polynom = 1 + x + x +  (x^2) + 2 + (x^2) + (x^5) - 3 - x - x
    
    //y.minDegree()
    y.minDegree should be(2)
    y.maxDegree should be(5)
    y(5) should be(Some(1))
    y(2) should be(Some(2))
    y(-1) should be(None)
    
    println(y) // should print 2x^2+1x^5
    
    
    
  }
  


}