package simulation

import simulation.Duration._
import simulation.constants._
import simulation.api.Population

class PersonSimulator(val pop: Population, val index: Int) {
  val p = pop(index)
  val model = pop.model

  birth()

  def birth() {
    if (p.birthDate <= 0 && p.birthDate + p.lifeTime > 0) {
      live()
    } else if (p.birthDate > 0) {
      model.wait(p.birthDate) {
        counter.born += 1
        pop.born(index)
        live()
      }
    }
  }

  def live() {
    getAJob
    behaveScandalouslyFun
    goOut
    if (p.isCrazy) {
      beCrazy
    } else {
      becomeCrazy
    }
    changeSocialRank
    if (p.socialRank == 3) {
      beSomebodyWithLotsOfMoneyOrPower
    }
    die
  }

  def getAJob() {
    model.wait(random.between(1, newJob years)) {
      counter.job += 1
      if (p.alive) {
        p.haveJob = true
        if (p.subscribed || (!p.subscribed && random.happens(subscribeAfterJob*(pop.subscribed.size.toDouble / pop.population.size)))) {
          pop.subscribe(index)
          pop.addRandomFriends(index, random.between(1, 500))
        }
        getAJob()
      }
    }
  }

  def behaveScandalouslyFun() {
    model.wait(random.between(1, leavingIfBehaveScandalously years)) {
      counter.scandalous += 1
      if (p.alive) {
        pop.unsubscribe(index)
        behaveScandalouslyFun()
      }
    }
  }

  def goOut() {
    model.wait(random.between(1, 10 years)) {
      counter.nicegirl += 1
      if (p.alive) {
        val numberOfGirls = random.between(0, maxGirls)
        if (numberOfGirls > 0 && random.happens(pop.subscribed.size.toDouble / pop.population.size)) {
          pop.subscribe(index)
        }
        pop.addRandomFriends(index, numberOfGirls)
        goOut()
      }
    }
  }

  def becomeCrazy() {
    model.wait(random.between(1, brainDefective  years)) {
      counter.crazy += 1
      if (p.alive && !p.isCrazy) {
        p.isCrazy = true
        beCrazy()
      }
    }
  }

  def beCrazy() {
    model.wait(random.between(1 , 2 years)) {
      counter.becrazy += 1
      if (p.alive && p.isCrazy) {
        if (p.subscribed) {
          val friendsRemoved = random.between(0, maxFriendsLeavingIfCrazy)
          for (i <- 1 to friendsRemoved; if p.subscribed) {
            pop.removeRandomFriend(index)
            if (pop.friendship(index).size == 0) {
              pop unsubscribe index
            }
          }
        }
        beCrazy()
      }
    }
  }

  def changeSocialRank() {
    model.wait(random.between(1, socialRankChange years)) {
      counter.change += 1
      if (p.alive) {
        if (random.happens(rankUp)) {
          if (p.socialRank < 3) {
            p.socialRank += 1
          }
        } else { // if rank down
          if (p.socialRank > 0) {
            p.socialRank -= 1
          }
        }
        changeSocialRank()
      }
    }
  }

  def beSomebodyWithLotsOfMoneyOrPower() {
    model.wait(random.between(1 , 2 years)) {
      counter.power += 1
      if (p.alive && p.socialRank == 3) {
        if (p.subscribed) {
          pop.addRandomFriends(index, random.between(0, 100))
          pop.addRandomFriendsAndSubscribers(index, random.between(0, 10))
        }
        beSomebodyWithLotsOfMoneyOrPower()
      }
    }
  }

  def die() {
    model.wait(p.lifeTime) {
      counter.die += 1
      pop.kill(index)
    }
  }

}

object counter {
  var born = 0
  var job = 0
  var scandalous = 0
  var nicegirl = 0
  var crazy = 0
  var becrazy = 0
  var change = 0
  var power = 0
  var die = 0
}