package simulation

object Duration {
  def apply(amount: Int) = new Duration(amount)
  implicit def int2Duration(amount: Int) = Duration(amount)
}

class Duration(val amount: Int) {
  def days = amount
  def weeks = 7 * amount
  def months = 30 * amount
  def years = 365 * amount
}