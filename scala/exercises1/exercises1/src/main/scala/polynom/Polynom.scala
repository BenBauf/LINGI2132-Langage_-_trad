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
    //??? //p.coefficients.
    
    val it = coefficients.iterator
    /*for (val row <- it){
            println(row)
        }*/
    while(it.hasNext){
      val degree = it.next
      ???
    }
    ???
  }

  /** 
   * Returns the subtraction of this `Polynom` with the `Polynom` p.
   * Example: (1 + 2x + 5x^2) - (4x + x^3) = 1 - 2x + 5x^2 - x^3
   */
  def -(p: Polynom): Polynom = ???

  /** 
   * Returns the addiction of this `Polynom` with the `Polynom` p
   * Example: (1 + 2x + 5x^2) + (4x + x^3) = 1 + 6x + 5x^2 + x^3
   */
  def +(p: Polynom): Polynom = {
    val sizeThis = coefficients.size-1
    val sizeP = p.coefficients.size-1
    val plusgrand = Math.max(sizeThis, sizeP)
    var pol:Array[Int] = new Array[Int](plusgrand+1)
    for(i<-0 to Math.min(sizeThis, sizeP)){
      pol(i)=p.coefficients(i)+this.coefficients(i)
    }
    if(sizeThis>sizeP){
      for(i<-sizeP+1 to sizeThis){
    	  pol(i)=this.coefficients(i)
      }
    }
    else{
      for(i<-sizeThis+1 to sizeP){
    	  pol(i)=p.coefficients(i)
      }
    }
    
    new Polynom(pol)
  }

  /** 
   * Returns the maximum degree of this `Polynom`.
   * Example: the maximum degree of 4x + x^3 is 3
   */
  def maxDegree: Int = coefficients.size - 1

  /** 
   * Returns the minimum degree of this `Polynom`.
   * Example: the minimum degree of 1 + 4x + x^3 is 0
   */
  def minDegree: Int = {
     for( i <- 0 to coefficients.size-1){      
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
    case p: Polynom => {
      if(coefficients.size!=p.coefficients.size){
    	  false
      }
      for( i <- 0 to coefficients.size-1){
        if(coefficients(i)!=p.coefficients(i)){false}
      }
      true    
    }
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