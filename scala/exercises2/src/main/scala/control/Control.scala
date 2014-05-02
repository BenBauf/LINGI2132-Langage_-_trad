package control

object Control {
  def loop(range: Range)(body: Int => Unit): UnitExc =
      new UnitExc(range, body)
  
  class UnitExc(range : Range, body: Int => Unit) {
    def onException(exc: => Unit) {
      for (i <- range) {
          try {
          body(i)
          } catch {
           case _: Throwable => {exc}
          }
        }
    }
  }
}
 