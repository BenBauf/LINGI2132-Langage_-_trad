package stream


abstract class MyStream {
    import MyStream._

    def isEmpty: Boolean
    def head: Int
    def tail: MyStream

    def apply(i: Int): Int = {
      if (i < 0) throw new NoSuchElementException("")
      else if (i == 0) head
      else tail(i-1)
    }
    def filter(p: Int => Boolean): MyStream = {
      ???
    }
    
    /**
     * take the n first entry of this stream to create a finite stream of at most n entry
     */
    def take(n: Int): MyStream = {
      take(n+1,this)
    }
    
    private def take(n: Int, s: MyStream): MyStream= {
      if (n <1)
        empty
      else
        cons(s.head,take(n-1,s.tail))
    } 
    
    /**
     * apply the f function on each entry of the stream
     * this allow us to use iterate on Mystream object using for loops
     */
    def foreach[U](f: Int => U): Unit = {
    	???
    }
}

object MyStream {
    def cons(hd: Int, tl: => MyStream) = new MyStream {
      def isEmpty = false
      def head: Int = hd
      def tail: MyStream = tl

    }
    val empty = new MyStream {
      def isEmpty = true
      def head = throw new NoSuchElementException("empty.head")
      def tail = throw new NoSuchElementException("empty.tail")
    }
}

object StreamUtils {
  
  import MyStream._
  
  def streamFrom(from: Int): MyStream = cons(from,empty)
  
  /**
   * Return an infinite fibonacci stream: a, b, a+b, b+a+b, a+b+b+a+b, b+a+b+a+b+b+a+b, ...
   */
  def fibonacci(a: Int, b: Int): MyStream = cons(a,cons(b,fibonacci(a+b,b+a+b)))
  
}

  
  