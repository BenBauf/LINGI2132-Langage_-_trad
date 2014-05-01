package polynom


class Polynom(coefs: Int*) {
  
    // coefficients(0) + coefficients(1) * x + coefficients(2) * x^2 + ... 
    private val coefficients: Vector[Int] = coefs.reverse.dropWhile(_ == 0).reverse.toVector
    
    /**
     * Return the coefficient related to this degree.
     * Example: 1+2x+5x^2 
     * p(0)=Some(1) p(1)=Some(2) p(10)=0 p(-1)=None
     */
    def apply(i: Int) : Option[Int] = ???
    
    
    
    def -(p: Polynom): Polynom = {
      this + new Polynom(p.coefficients.map(-_):_*)
    }
    
    def +(p: Polynom): Polynom = ???

	def maxDegree: Int = ???
	
	def minDegree: Int = ???
	
	override def hashCode = coefficients.hashCode
	
    override def equals(other: Any) = other match { 
      case that: Polynom => {
        maxDegree == that.maxDegree && (0 until maxDegree).forall(i => coefficients(i) == that.coefficients(i))
      }
      case _ => false 
    }
	
	override def toString(): String = {
	  ???
	}

}

object Polynom {
  
  def apply(coefs: Int*) = new Polynom(coefs:_*)
  
  def sum(polynoms: Iterable[Polynom]): Polynom = {
    polynoms.reduce((p1,p2) => p1 + p2)
  }
  
  // TODO
  
  // define an x object 
  
  // define implicit convertions for constants  (see test suite)
}

