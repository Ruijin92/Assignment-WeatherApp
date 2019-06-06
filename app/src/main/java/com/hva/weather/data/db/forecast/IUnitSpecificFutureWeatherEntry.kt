package com.hva.weather.data.db.forecast

import org.threeten.bp.LocalDate

interface IUnitSpecificFutureWeatherEntry {
    val date: LocalDate
    val avgTemp: Double
    val condition: String
}