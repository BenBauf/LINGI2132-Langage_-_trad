package myutils

object Utils {
  
  /**
   * Remove out of l every element that satisfies the predicate
   * hint: Use function filter on list to implement it
   */
  def filterOut(l: List[Int], predicate : Int => Boolean): List[Int] = {
    l.filterNot(predicate)
  }
  
  /**
   * Concatenate l1 and l2 pairwise and keep only the pairs satisfying the predicate
   * hint: use zip and filter
   */
  def zipAndFilter(l1: List[Int], l2: List[Int], predicate: (Int,Int) => Boolean): List[(Int, Int)] = {
    def pred2(p : (Int, Int)): Boolean = predicate(p._1, p._2)
    (l1 zip l2).filter(pred2)
  }
  
  /**
   * Check if the predicate is true for all element of the list
   * hint: implement it using the foldLeft function
   */
  def trueForAll(l1: List[Int], predicate: Int => Boolean): Boolean = {
    l1.foldLeft(true){
      (z, i) => predicate(i)&(z) 
    }
  }
  
  /**
   * Convert this string to lower case
   * hint: use the map function
   */
  def toLowerCase(l: String): String = {
    l.map(c => c.toLower)
  }
  
  abstract class Fruit
  object Apple extends Fruit
  object Orange extends Fruit
  object Banana extends Fruit
  
  /**
   * Return a new list where Apple become Oranges and Oranges become apples, other fruits are unchanged
   * hint: use the map function with match statement
   */
  def flip(l: List[Fruit]): List[Fruit] = {
    l.map( f =>
    	f match {
	    case Apple => Orange 
	    case Orange => Apple
	    case _ => f
	  }
    )
  }  
}