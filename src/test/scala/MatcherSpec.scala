import matching.Matcher
import model.Order
import org.scalatest.FlatSpec

class MatcherSpec extends FlatSpec {

  val orders = Seq(
    Order("C1", 'b', 'A', 10, 2),
    Order("C2", 's', 'A', 10, 2),
    Order("C3", 's', 'A', 5, 3),
    Order("C3", 's', 'A', 5, 3),
    Order("C4", 'b', 'B', 10, 1),
    Order("C5", 's', 'D', 10, 1)
  )

  val result = Matcher.getMatchedOrders(orders)
  val (first, second) = result.head

  "A getMatchedOrders method of Matcher" should "return only (sellOrder, buyOrder) pairs" in {
    assert(result.size == 1)
    assert(first.operation == 's' && second.operation == 'b')
  }

  it should "return only pairs mapped by type, price and amount of order stocks" in {
    assert(first.stockType == second.stockType &&
      first.price == second.price &&
      first.amount == second.amount)
  }
}
