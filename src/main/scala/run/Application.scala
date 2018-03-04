package run

import matching.{BalanceComputer, Matcher}
import utils.{Reader, Writer}

object Application extends App{

  val clientsBalances = Reader.readClientsBalances("data/clients.txt")
  val orders = Reader.readOrders("data/orders.txt")

  val matchedOrders = Matcher.getMatchedOrders(orders)
  val resultBalances = BalanceComputer.computeBalances(clientsBalances, matchedOrders)

  Writer.writeClientsBalances("data/result.txt", resultBalances)
}
