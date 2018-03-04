import matching.BalanceComputer
import model.{Balance, Order}
import org.scalatest.FlatSpec

class BalanceComputerSpec extends FlatSpec {

  "A computeBalances method of BalanceComputer" should "return balances of each client after orders processing" in {

    val matchedOrders = Seq(
      (Order("C1", 'b', 'A', 10, 2), Order("C2", 's', 'A', 10, 2)),
      (Order("C3", 'b', 'B', 5, 1), Order("C1", 's', 'B', 5, 1)),
      (Order("C4", 'b', 'B', 10, 1), Order("C3", 's', 'B', 10, 1))
    )
    val initBalances = Map(
      "C1" -> Balance(100, Map('A' -> 1, 'B' -> 2, 'C' -> 0, 'D' -> 0)),
      "C2" -> Balance(10, Map('A' -> 2, 'B' -> 0, 'C' -> 0, 'D' -> 0)),
      "C3" -> Balance(5, Map('A' -> 0, 'B' -> 1, 'C' -> 0, 'D' -> 0)),
      "C4" -> Balance(10, Map('A' -> 0, 'B' -> 0, 'C' -> 0, 'D' -> 0))
    )

    val expectedResBalances = Map(
      "C1" -> Balance(85, Map('A' -> 3, 'B' -> 1, 'C' -> 0, 'D' -> 0)),
      "C2" -> Balance(30, Map('A' -> 0, 'B' -> 0, 'C' -> 0, 'D' -> 0)),
      "C3" -> Balance(10, Map('A' -> 0, 'B' -> 1, 'C' -> 0, 'D' -> 0)),
      "C4" -> Balance(0, Map('A' -> 0, 'B' -> 1, 'C' -> 0, 'D' -> 0))
    )

    assertResult(expectedResBalances) {
      BalanceComputer.computeBalances(initBalances, matchedOrders)
    }
  }
}
