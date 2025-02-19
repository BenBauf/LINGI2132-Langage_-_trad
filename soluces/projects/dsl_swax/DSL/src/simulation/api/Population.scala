package simulation.api
import simulation.constants._
import simulation.Duration._
import simulation.Person
import simulation.SimModel
import simulation.random
import simulation.PersonSimulator

object Population {
  def apply(maxPop: Int) = new Population(maxPop)
}

class Population(MaximumSize: Int = 5000) {

  val model = new SimModel()

  val population = new Array[Person](MaximumSize) //all the population
  val alive = collection.mutable.Buffer[Int]()
  val subscribed = collection.mutable.Buffer[Int]()
  val friendship = collection.mutable.Map[Int, collection.mutable.Buffer[Int]]() //friendlists

  var subscribeInit = 0.2
  var employedInit = 0.8
  var crazyInit = 0.01
  var highRankInit = 0.05
  var lowRankInit = 0.25
  var simDuration = 5 years

  def displayRank() {
    model.showRank = true
  }
  def displayAlive() {
    model.showAlive = true
  }
  def displayEmployed() {
    model.showEmployment = true
  }
  def displayCrazy() {
    model.showCrazy = true
  }
  def displayMFN() {
    model.showMFN = true
  }
  def displaySubscribe() {
    model.showSubscribed = true
  }

  def init() {

    for (i <- 0 to MaximumSize - 1) {
      val birthDate = random.between(-100 years, simDuration)
      val lifeTime = random.between(1 years, 100 years)
      population(i) = Person(birthDate, lifeTime)
      if (birthDate <= 0) {
        born(i)
        if (random.happens(subscribeInit)) {
          subscribe(i)
        }
      }
      if (birthDate <= -18 && random.happens(employedInit)) {
        population(i).haveJob = true
      }
      if (random.happens(crazyInit)) {
        population(i).isCrazy = true
      }
      population(i).socialRank = random.which(lowRankInit, 1 - lowRankInit - highRankInit, highRankInit)
      new PersonSimulator(Population.this, i)
    }

    for (i <- subscribed) yield {
      addRandomFriends(i, random.between(0, 100))
    }

  }

  def simulate() {
    model.simulate(simDuration, this)
  }

  def apply(index: Int): Person = {
    return population(index)
  }

  def born(index: Int) {
    alive += index
    population(index).alive = true
  }

  def kill(index: Int) {
    alive -= index
    population(index).alive = false
  }

  def subscribe(index: Int) {
    if (!population(index).subscribed) {
      subscribed += index
      population(index).subscribed = true
      friendship += (index -> collection.mutable.Buffer[Int]())
    }
  }

  def unsubscribe(index: Int) {
    if (population(index).subscribed) {
      subscribed -= index
      population(index).subscribed = false
      friendship -= index
    }
  }

  def addFriend(id1: Int, id2: Int) {
    if (population(id1).subscribed && population(id2).subscribed) {
      friendship(id1) += id2
      friendship(id2) += id1
    }
  }

  def removeFriend(id1: Int, id2: Int) {
    if (population(id1).subscribed && population(id2).subscribed) {
      friendship(id1) -= id2
      friendship(id2) -= id1
    }
  }

  def removeRandomFriend(id: Int) {
    if (population(id).subscribed) {
      val friendlist = friendship(id)
      if (friendlist.size != 0) {
        removeFriend(id, friendlist(random.between(0, friendlist.size)))
      }
    }
  }

  def addRandomFriendsAndSubscribers(id: Int, max1: Int) {
    var max = max1
    if (population(id).subscribed) {
      var notFriends = alive -- friendship(id)
      while (notFriends.size > 0 && max > 0) {
        val id2 = notFriends(random.between(0, notFriends.size))
        subscribe(id2)
        addFriend(id, id2)
        notFriends -= id2
        max -= 1
      }
    }
  }

  def addRandomFriends(id: Int, max1: Int) {
    var max = max1
    if (population(id).subscribed) {
      var notFriends = subscribed -- friendship(id)
      while (notFriends.size > 0 && max > 0) {
        val id2 = notFriends(random.between(0, notFriends.size))
        addFriend(id, id2)
        notFriends -= id2
        max -= 1
      }
    }
  }

  def getAliveNbr() = {
    alive.size
  }

  def getSubscribedNbr() = {
    subscribed.size
  }

  def getMeanFriendNbr() = {
    var mean = 0
    for ((index, buf) <- friendship) {
      mean += buf.size
    }
    if (friendship.size == 0) {
      0
    } else {
      mean / friendship.size
    }
  }

  //O(n), do it one time for all remaining stats
  def getStats() = {
    val aliveNbr = getAliveNbr
    val subscribedNbr = getSubscribedNbr
    val meanFriendNbr = getMeanFriendNbr

    var lsr, msr, hsr = 0
    var employedNbr = 0
    var crazyNbr = 0
    for (index <- alive ; person = population(index)) {
      if (person.socialRank == lowSocialRank) {
        lsr += 1
      } else if (person.socialRank == mediumSocialRank) {
        msr += 1
      } else {
        hsr += 1
      }
      if (person.haveJob) {
        employedNbr += 1
      }
      if (person.isCrazy) {
        crazyNbr += 1
      }
    }
    Stats(lsr, msr, hsr, employedNbr, crazyNbr, aliveNbr, subscribedNbr, meanFriendNbr)
  }
}

object Stats {
  def apply(lsrNbr: Int, msrNbr: Int, hsrNbr: Int, employedNbr: Int,
    crazyNbr: Int, aliveNbr: Int, subscribedNbr: Int, meanFriendNbr: Int) =
    new Stats(lsrNbr, msrNbr, hsrNbr, employedNbr, crazyNbr, aliveNbr, subscribedNbr, meanFriendNbr)
}

class Stats(
  val lowSocialRank: Int,
  val medSocialRank: Int,
  val highSocialRank: Int,
  val employed: Int,
  val crazy: Int,
  val alive: Int,
  val subscribed: Int,
  val meanFriendNumber: Int) 

