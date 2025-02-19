
package simulation

import scala.collection.mutable.PriorityQueue
import simulation.api.Stats
import simulation.api.Population
import org.jfree.data.general.DefaultPieDataset
import org.jfree.chart.ChartFactory
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import scala.collection.mutable.Queue
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.ChartPanel
import simulation.api.Population
import javax.swing.JFrame
import javax.swing.JPanel
import simulation.constants._
import javax.swing.SwingUtilities
import java.awt.GridLayout

class Point(val value: Int, val time: Double) {}

object Point {
  def apply(value: Int, time: Double) = new Point(value, time)
}

class SimModel() {

  var showAlive = false
  var showCrazy = false
  var showEmployment = false
  var showRank = false
  var showSubscribed = false
  var showMFN = false

  private val eventQueue = new PriorityQueue[SimEvent]()
  private var currentTime = 0.0

  def clock = currentTime

  val aliveRecord = new XYSeries(0)
  val crazyRecord = new XYSeries(0)
  val empRecord = new XYSeries(0)
  val lowRecord = new XYSeries(0)
  val medRecord = new XYSeries(1)
  val subRecord = new XYSeries(0)
  val highRecord = new XYSeries(2)
  val MFNRecord = new XYSeries(0)

  def recordStats(stats: Stats, days: Double) {
    val time = days / 365
    if (showAlive) {
      aliveRecord.add(time, stats.alive)
    }
    if (showCrazy) {
      crazyRecord.add(time, stats.crazy * 100.0 / stats.alive)
    }
    if (showEmployment) {
      empRecord.add(time, stats.employed * 100.0 / stats.alive)
    }
    if (showRank) {
      lowRecord.add(time, stats.lowSocialRank * 100.0 / stats.alive)
      medRecord.add(time, (stats.medSocialRank + stats.lowSocialRank) * 100.0 / stats.alive)
      highRecord.add(time, (stats.highSocialRank + stats.lowSocialRank + stats.medSocialRank) * 100.0 / stats.alive)
    }
    if (showSubscribed) {
      subRecord.add(time, stats.subscribed * 100.0 / stats.alive)
    }
    if (showMFN) {
      MFNRecord.add(time, stats.meanFriendNumber)
    }
  }

  def simulate(horizon: Int, population: Population) {

    SwingUtilities.invokeLater(new Runnable() {
      def run() {
        var nStats = 0
        if (showAlive) {
          nStats += 1
        }
        if (showCrazy) {
          nStats += 1
        }
        if (showEmployment) {
          nStats += 1
        }
        if (showRank) {
          nStats += 1
        }
        if (showSubscribed) {
          nStats += 1
        }
        if (showMFN) {
          nStats += 1
        }
        if (nStats != 0) {
          val frame: JFrame = new JFrame()
          var row = 0
          var col = 0
          if (nStats == 1) row = 1 else row = 2
          if (nStats <= 2) col = 1 else if (nStats == 3 || nStats <= 4) col = 2 else col = 3
          val gridPanel = new JPanel(new GridLayout(row, col))
          if (showAlive) {
            gridPanel.add(new ChartPanel(ChartFactory.createXYLineChart("Size of population", "Time", "Persons", new XYSeriesCollection(aliveRecord), PlotOrientation.VERTICAL, false, false, false)))
          }
          if (showCrazy) {
            gridPanel.add(new ChartPanel(ChartFactory.createXYLineChart("Number of crazy persons", "Time", "Persons", new XYSeriesCollection(crazyRecord), PlotOrientation.VERTICAL, false, false, false)))
          }
          if (showEmployment) {
            gridPanel.add(new ChartPanel(ChartFactory.createXYLineChart("Employment", "Time", "Employed persons", new XYSeriesCollection(empRecord), PlotOrientation.VERTICAL, false, false, false)))
          }
          if (showRank) {
            val coll = new XYSeriesCollection()
            coll.addSeries(lowRecord)
            coll.addSeries(medRecord)
            coll.addSeries(highRecord)
            val panel = new ChartPanel(ChartFactory.createXYLineChart("Rank", "Time", "Persons", coll, PlotOrientation.VERTICAL, false, false, false))
            gridPanel.add(panel)
          }
          if (showSubscribed) {
            gridPanel.add(new ChartPanel(ChartFactory.createXYLineChart("Subscribed", "Time", "Subscribed persons", new XYSeriesCollection(subRecord), PlotOrientation.VERTICAL, false, false, false)))
          }
          if (showMFN) {
            gridPanel.add(new ChartPanel(ChartFactory.createXYLineChart("Mean friend number", "Time", "Mean friend number", new XYSeriesCollection(MFNRecord), PlotOrientation.VERTICAL, false, false, false)))

          }
          frame.add(gridPanel)
          frame.setVisible(true)
          frame.pack()
          frame.setLocationRelativeTo(null)
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        }
      }
    })

    var statsInterval = horizon / 1000
    var intervalCounter = 0
    var lastTime = 0.0

    recordStats(population.getStats, 0)

    while (eventQueue.nonEmpty) {
      val e = eventQueue.dequeue()
      currentTime = e.time
      if (currentTime <= horizon) {
        if (lastTime < currentTime) {
          recordStats(population.getStats, lastTime)
        }
        e.process()
        lastTime = currentTime
      } else {
        currentTime = horizon
        //displayStats()
        return
      }
    }
  }

  def wait(duration: Int)(block: => Unit) {
    eventQueue += new SimEvent(clock + duration, block)
  }
}