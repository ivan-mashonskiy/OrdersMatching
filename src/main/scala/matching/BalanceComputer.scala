package matching

import model.{Balance, Order}

object BalanceComputer {

  private def getChangeMultiplier(op: Char): Int = op match {
    case 'b' => -1
    case 's' => 1
  }

  /**
    * Computes balances of each client after orders processing
    *
    * @param initBalances - clients' balances before orders processing
    * @param matchedOrders - sequence of sell-buy orders matched by type, price and amount of order stocks
    *                      and obtained by [[matching.Matcher]]
    * @return - clients' balances after orders processing
    */
  def computeBalances(initBalances: Map[String, Balance],
                      matchedOrders: Seq[(Order, Order)]): Map[String, Balance] = {
    val flatOrders = matchedOrders.flatMap{ case (first, second) => Seq(first, second) }
    val clientsOrders = flatOrders.groupBy(_.clientName)
    val balances = clientsOrders.map { case (clientName, orders) =>
      val clientBalanceChanges = orders.map { case Order(_, op, stockType, price, amount) =>
        val changeMultiplier = getChangeMultiplier(op)
        val dollarsChange = changeMultiplier * price * amount
        val amountChange = -changeMultiplier * amount
        Balance(dollarsChange, Map(stockType -> amountChange))
      }
      val resBalance = clientBalanceChanges.fold(initBalances(clientName)) {
        case (Balance(fDollars, fStockAmounts), Balance(sDollars, sStockAmounts)) =>
          val stockAmounts = fStockAmounts ++ sStockAmounts.map { case (k, v) =>
            k -> (v + fStockAmounts.getOrElse(k, 0))
          }
          Balance(fDollars + sDollars, stockAmounts)
      }
      clientName -> resBalance
    }
    balances
  }
}
