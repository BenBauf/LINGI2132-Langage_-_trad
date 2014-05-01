package polynom

class Polynom(coefs: Int*) {
  
    // coefficients(0) + coefficients(1) * x + coefficients(2) * x^2 + ... 
    private val coefficients: Vector[Int] = coefs.reverse.dropWhile(_ == 0).reverse.toVector
    
    /**
     * Return the coefficient related to this degree.
     * Example: 1+2x+5x^2 
     * p(0)=Some(1) p(1)=Some(2) p(10)=0 p(-1)=None
     */
    def apply(i: Int) : Option[Int] = {
      try{
    	  Some(coefficients(i))
      }
      catch{
          case e: Exception => None
      }
    }
    
    def -(p: Polynom): Polynom = {
      this + new Polynom(p.coefficients.map(-_):_*)
    }
    
    /** 
   * Returns the addiction of this `Polynom` with the `Polynom` p
   * Example: (1 + 2x + 5x^2) + (4x + x^3) = 1 + 6x + 5x^2 + x^3
   */
  def +(p: Polynom): Polynom = {
    val smallP=if (p.coefficients.size > this.coefficients.size) this;else p;
    val bigP=if (p.coefficients.size > this.coefficients.size) p;else this;
    var pol:Array[Int] = new Array[Int](bigP.coefficients.size)
    for(i<-0 until smallP.coefficients.size){
      pol(i)=p.coefficients(i)+this.coefficients(i)
    }
    for(i<-smallP.coefficients.size until bigP.coefficients.size){
    	  pol(i)=bigP.coefficients(i)
      }
     //println("final "+new Polynom(pol.iterator).toString())
    new Polynom(pol.map(+_):_*)
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
        return i
      }      
    }
    return 0
  }
	
	override def hashCode = coefficients.hashCode
	
    override def equals(other: Any) = other match { 
      case that: Polynom => {
        maxDegree == that.maxDegree && (0 until maxDegree).forall(i => coefficients(i) == that.coefficients(i))
      }
      case _ => false 
    }
	
	override def toString(): String = {
	  for(i<-0 until coefficients.size){
	    print(coefficients(i))
	    print("x^")
	    print(i)
	    print("+")
	  }
	  println();
	  ""
	   //coefficients.zipWithIndex.filter(p => p._2 != 0).map(p => p._2 + "x^" + p._1).mkString(" + ")
	}

}

object Polynom {
  
  def apply(coefs: Int*) = new Polynom(coefs:_*)
  
  def sum(polynoms: Iterable[Polynom]): Polynom = {
    polynoms.reduce((p1,p2) => p1 + p2)
  }
 
  
  
  object x extends Polynom(0,1){
   
		
    def ^(expo: Int): Polynom = {
      var pol:Array[Int] = new Array[Int](expo+1) 
      pol(expo)=1
      new Polynom(pol.map(+_):_*)
    }
    
  }
  implicit def Int2x(value : Int) = {
       var pol:Array[Int] = new Array[Int](1)
       pol(0)=value
       new Polynom(pol.map(+_):_*)
    }

}

