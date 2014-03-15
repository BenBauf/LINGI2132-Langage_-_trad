package simulation.api

import simulation.Duration._

object SimulationApiTest extends App {
  val dayDuration = 10 years
  val population = Population(5000)
  population.simDuration = dayDuration
  population.crazyInit = 0.02
  population.init
  population.displayAlive
  population.simulate
}