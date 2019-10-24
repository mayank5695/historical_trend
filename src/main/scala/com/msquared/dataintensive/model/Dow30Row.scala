package com.msquared.dataintensive.model

import java.time.LocalDate

case class Dow30Row(
                     date: LocalDate,
                     high: Double,
                     low: Double,
                     open: Double,
                     close: Double,
                     adj_close: Double,
                     volume: Long
                   )