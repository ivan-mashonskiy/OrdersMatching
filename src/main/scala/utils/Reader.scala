package utils

import model.{Balance, Order}

import scala.io.Source

object Reader {

  private def readData(filename: String) = Source.fromFile(filename).getLines().map(_.split("\t"))

  def readClientsBalances(filename: String): Map[String, Balance] = {
    val rawData = readData(filename)
    rawData.map { data =>
      (data(0) -> Balance(data(1).head,
        Map(
          'A' -> data(2).toInt,
          'B' -> data(3).toInt,
          'C' -> data(4).toInt,
          'D' -> data(5).toInt
        )))
    }.toMap
  }

  def readOrders(filename: String): Seq[Order] = {
    val rawData = readData(filename)
    rawData.map { data =>
      Order(data(0),
        data(1).head,
        data(2).head,
        data(3).toInt,
        data(4).toInt)
    }.toSeq
  }
}
