package matching

import model.Order

object Matcher {

  /**
    * Matches orders to sell-buy pairs by the type, price and amount of stocks
    *
    * @param orders - sequence of orders
    * @return sequence of sell-buy orders matched by type, price and amount of order stocks
   */
  def getMatchedOrders(orders: Seq[Order]): Seq[(Order, Order)] = {
    val groupedOrders = orders.groupBy(order => (order.stockType, order.price, order.amount)).map { case (_, v) => v }
    val matchedOrders = groupedOrders.flatMap { orders =>
      val (sellOrders, buyOrders) = orders.partition(order => order.operation == 's')
      val matches = sellOrders.zip(buyOrders)
      matches
    }
    matchedOrders.toSeq
  }
}
