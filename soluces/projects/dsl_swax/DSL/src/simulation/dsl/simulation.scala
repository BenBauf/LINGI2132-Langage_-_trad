package simulation.dsl

import simulation.api.Population
import simulation.dsl.Percentage._

object Percentage {
  val powerful = 1
  val crazy = 2
  val poor = 3
  val employed = 4
  val subscribed_to_network = 5
  val evolution = new SimBuilder()
  val stats = new StatsBuilder()
  def apply(p: Int) = new Percentage(p)
  implicit def int2Percentage(i: Int) = Percentage(i)
}

class Percentage(val i:Double) {
  
  var pop = Population(0)
  
  def percent_of(p: Population) : Percentage = {
    pop = p
    return this
  }
  
  def is(attr: Int) {
    if(attr == powerful) {
      pop.highRankInit = i / 100
    } else if(attr == crazy) {
      pop.crazyInit = i / 100
    } else if(attr == employed) {
      pop.employedInit = i / 100
    } else if(attr == subscribed_to_network) {
      pop.subscribeInit = i / 100
    } else if(attr == poor) {
      pop.lowRankInit = i / 100
    }
  }
  
}

class SimBuilder() {
  var pop = Population(0)
  def of(p: Population) : SimBuilder = {
    pop = p
    return this
  }
  
def during(d: Int) : Simulator = {
    pop.simDuration = d
    pop.init
    return new Simulator(pop)
  }
  
}

class Simulator(val pop:Population) {
}

object StatsBuilder {
  var sb: StatsBuilder = new StatsBuilder()
  def rank() {
    sb.sim.pop.displayRank()
  }

  def population_size() {
    sb.sim.pop.displayAlive()
  }

  def employement() {
    sb.sim.pop.displayEmployed()
  }

  def crazyness() {
    sb.sim.pop.displayCrazy()
  }

  def subscribed() {
    sb.sim.pop.displaySubscribe()
  }

  def mean_friend_number() {
    sb.sim.pop.displayMFN()
  }
}

class StatsBuilder() {
  var sim = new Simulator(Population(0))
  def of(s: Simulator): StatsBuilder = {
    sim = s
    StatsBuilder.sb = this
    return this
  }
  def to_be_displayed(block: => Unit) {
    block
    sim.pop.simulate
  }
}