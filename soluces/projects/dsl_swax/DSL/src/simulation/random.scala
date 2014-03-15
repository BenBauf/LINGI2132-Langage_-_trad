package simulation

import scala.util.Random

object random {
  val rand = new Random()

  def happens(proba: Double) = {
    val i: Int = rand.nextInt(1000000)
    i <= (proba * 1000000)
  }

  def which(p1: Double, p2: Double, p3: Double) = {

    val a1 = p1 * 100
    val a2 = (p2 + p1) * 100

    val i = rand.nextInt(100)
    if (i < a1) {
      1
    } else if (i < a2) {
      2
    } else {
      3
    }
  }

  def between(i: Int, j: Int) = {
    val t = rand.nextInt(j - i)
    i + t
  }
  
}