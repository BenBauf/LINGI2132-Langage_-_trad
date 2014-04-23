package solver.core

import scala.collection.JavaConversions
import org.sat4j.minisat.SolverFactory
import org.sat4j.specs.ISolver
import java.io.PrintWriter
import org.sat4j.core.VecInt

class SatSolver() {

  val sat4j: ISolver = SolverFactory.instance.createSolverByName("Default")

  def reset = sat4j.reset

  def addAllClauses(clauses: Seq[Seq[Int]]) = for (clause <- clauses) addClause(clause)

  def addClause(lits: Seq[Int]) = sat4j.addClause(new VecInt(lits.toArray))

  def isSatisfiable = sat4j.isSatisfiable

  def isSatisfiable(assumps: Seq[Int]) = sat4j.isSatisfiable(new VecInt(assumps.toArray))

  def model: Array[Int] = sat4j.model

  def model(v: Int) = sat4j.model(v)

  def findModel: Array[Int] = sat4j.findModel

  def findModel(assumps: Seq[Int]): Array[Int] = sat4j.findModel(new VecInt(assumps.toArray))

  def nVars = sat4j.nVars

  def nConstraints = sat4j.nConstraints

  def getStat = JavaConversions.mapAsScalaMap(sat4j.getStat).toMap

  def setTimeout(time: Int) = sat4j.setTimeout(time)

  def printInfos(out: java.io.PrintWriter) = sat4j.printInfos(out)

  def printStat(out: java.io.PrintWriter) = sat4j.printStat(out)
}
