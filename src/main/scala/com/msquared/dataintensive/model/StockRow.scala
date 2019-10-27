package com.msquared.dataintensive.model

import java.sql.Date

case class StockRow(
                     date: Date,
                     high: Option[Double],
                     low: Option[Double],
                     open: Option[Double],
                     close: Option[Double],
                     adj_close: Option[Double],
                     volume: Option[Long]
                   )