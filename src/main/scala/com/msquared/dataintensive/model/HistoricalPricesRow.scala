package com.msquared.dataintensive.model

import java.time.LocalDate

case class HistoricalPricesRow(
                     date: LocalDate,
                     high: Double,
                     low: Double,
                     open: Double,
                     close: Double,
                     volume: Long
                   )