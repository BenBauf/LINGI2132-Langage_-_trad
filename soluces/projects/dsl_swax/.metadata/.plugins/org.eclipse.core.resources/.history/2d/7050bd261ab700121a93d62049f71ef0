package simulation

class SimEvent(var time: Double, block: => Unit) extends Ordered[SimEvent]{
		def process() = block
		def compare(E : SimEvent) = E.time.compare(this.time)
}
