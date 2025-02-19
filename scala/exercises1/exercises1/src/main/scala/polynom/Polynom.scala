package polynom

class Polynom(coefs: Traversable[Int]) {
  assert(!coefs.isEmpty, "no coefficient")

  // coefficients(0) + coefficients(1) * x + coefficients(2) * x^2 + ... 
  private val coefficients: Vector[Int] = coefs.toVector

  /**
   * Returns the coefficient related to `degree`.
   * Example: 1 + 2x + 5x^2
   * p(0) = 1
   * p(1) = 2 
   * p(5) = 0 
   */
  def apply(degree: Int): Int = {
    if (degree < 0) throw new IllegalArgumentException("negative degree")
    else coefficients(degree)
  }

  /** 
   * Returns the product of this `Polynom` with the `Polynom` p.
   * Example: (1 + 2x + 5x^2) * (4x) = 4x + 8x^2 + 20x^3
   */
  def *(p: Polynom): Polynom = {
    var pol:Array[Int] = new Array[Int](p.maxDegree+this.maxDegree+1)
    for(i<-0 until this.coefficients.size){
      for(j<-0 until p.coefficients.size){
        pol(i+j)+=this.coefficients(i)*p.coefficients(j)
      }
    }
    new Polynom(pol)
  }

  /** 
   * Returns the subtraction of this `Polynom` with the `Polynom` p.
   * Example: (1 + 2x + 5x^2) - (4x + x^3) = 1 - 2x + 5x^2 - x^3
   */
  def -(p: Polynom): Polynom = {
    val smallP=if (p.coefficients.size > this.coefficients.size) this;else p;
    val bigP=if (p.coefficients.size > this.coefficients.size) p;else this;
    var pol:Array[Int] = new Array[Int](bigP.coefficients.size)
    for(i<-0 until smallP.coefficients.size){
      pol(i)=p.coefficients(i)-this.coefficients(i)
    }
    for(i<-smallP.coefficients.size until bigP.coefficients.size){
    	  pol(i)=bigP.coefficients(i)
      }
    new Polynom(pol)
  }

  /** 
   * Returns the addiction of this `Polynom` with the `Polynom` p
   * Example: (1 + 2x + 5x^2) + (4x + x^3) = 1 + 6x + 5x^2 + x^3
   */
  def +(p: Polynom): Polynom = {
    val smallP=if (p.coefficients.size > this.coefficients.size) this;else p;
    val bigP=if (p.coefficients.size > this.coefficients.size) p;else this;
    var pol:Array[Int] = new Array[Int](bigP.coefficients.size)
    for(i<-0 to smallP.coefficients.size-1){
      pol(i)=p.coefficients(i)+this.coefficients(i)
    }
    for(i<-smallP.coefficients.size to bigP.coefficients.size-1){
    	  pol(i)=bigP.coefficients(i)
      }
    new Polynom(pol)
  }

  /** 
   * Returns the maximum degree of this `Polynom`.
   * Example: the maximum degree of 4x + x^3 is 3
   */
  def maxDegree: Int =  {
     for( i <- 0 until coefficients.size){ 
       if(coefficients(coefficients.size-1-i)!=0){
        return coefficients.size-1-i
      }      
    }
    return 0
  }

  /** 
   * Returns the minimum degree of this `Polynom`.
   * Example: the minimum degree of 1 + 4x + x^3 is 0
   */
  def minDegree: Int = {
     for( i <- 0 until coefficients.size){      
       if(coefficients(i)!=0){
        i
      }      
    }
    0
  }

  /** 
   * Returns true if this `Polynom` represents the same `Polynom` as that.
   */
  override def equals(that: Any) = that match {
    case p: Polynom => this.coefficients.equals(p.coefficients) 
    case _ => false
  }
  
  override def hashCode = coefficients.hashCode
  
  override def toString: String = {
    coefficients.zipWithIndex.filter(p => p._2 != 0).map(p => p._2 + "x^" + p._1).mkString(" + ")
  }

}

object Polynom {

	// coefs(0) + coefs(1) * x + coefs(2) * x^2 + ...
	def apply(coefs: Int*) = new Polynom(coefs)

	def sum(polynoms: Iterable[Polynom]): Polynom = {
		// hint, uses map/max/min/... to first compute coefficients
		???
	}
}