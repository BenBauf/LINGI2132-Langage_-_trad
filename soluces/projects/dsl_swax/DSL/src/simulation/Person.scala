package simulation
import simulation.constants._

object Person{
  def apply(birthDate: Int, lifeTime: Int) = new Person(birthDate, lifeTime)
}

class Person(val birthDate: Int,
			 val lifeTime: Int) {
  
  var alive = (birthDate <= 0) && (birthDate + lifeTime > 0)
  var subscribed = false
  
  var socialRank = 2
  var haveJob = false
  var isCrazy = false

}
