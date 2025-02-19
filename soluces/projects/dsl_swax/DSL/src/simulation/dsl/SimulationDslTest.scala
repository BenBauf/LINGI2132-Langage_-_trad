package simulation.dsl

import simulation.api.Population
import simulation.Duration._
import simulation.dsl.Percentage._
import simulation.dsl.StatsBuilder._
import simulation.api._

object SimulationDslTest extends App {

  val my_pop = Population(10000)

  2 percent_of my_pop is crazy
  5 percent_of my_pop is powerful
  20 percent_of my_pop is poor
  77 percent_of my_pop is employed
  5 percent_of my_pop is subscribed_to_network

  val simulator = evolution of my_pop during 50.years

  stats of simulator to_be_displayed {
    population_size
    crazyness
    employement
    rank
    subscribed
    mean_friend_number
  }

}
