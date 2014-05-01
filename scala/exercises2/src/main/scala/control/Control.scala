package control

object Control {
  def loop(n: Range)(body: => Int): Int = {	
    val i =0
    i
	  /*if (n > 0) {	
		  println(n)	
		  body	
		  loop(n-1) (body)	
	  }	*/
  }	
  
  object i{
    
  }

  
  /*
    
  TODO: 
    
  Create Scala constructs to make this new control block work
   
  loop(1 to 5) { i =>
    
  } onException {
    
  }   
   
   
   This code iterates over a Scala range, 
   i is the current value if iteration passed as argument to the closure.
   If an exception is thrown, it is catched and the the onException block is executed
   before continuing on next value of the range
   
   Hint: Don't hesitate to create intermediate classes
  
  */
  



}