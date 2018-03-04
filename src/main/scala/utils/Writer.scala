package utils

import java.io.{File, PrintWriter}

import model.Balance

object Writer {

  def writeClientsBalances(filename: String, clientsBalances: Map[String, Balance]) = {
    val fw = new PrintWriter(new File(filename))
    clientsBalances.foreach { case (clientName, balance) =>
      fw.write(s"${clientName}\t${balance.dollars}\t" +
        s"${balance.stockAmounts.getOrElse('A', 0)}\t" +
        s"${balance.stockAmounts.getOrElse('B', 0)}\t" +
        s"${balance.stockAmounts.getOrElse('C', 0)}\t" +
        s"${balance.stockAmounts.getOrElse('D', 0)}\n")
    }
    fw.close()
  }
}
