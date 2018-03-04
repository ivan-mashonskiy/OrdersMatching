package model

case class Order(clientName: String,
                 operation: Char,
                 stockType: Char,
                 price: Int,
                 amount: Int
                )
