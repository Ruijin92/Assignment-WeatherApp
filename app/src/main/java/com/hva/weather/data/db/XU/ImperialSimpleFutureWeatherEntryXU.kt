package com.hva.weather.data.db.XU

import androidx.room.ColumnInfo
import com.hva.weather.data.db.forecast.IUnitSpecificFutureWeatherEntry
import org.threeten.bp.LocalDate


class ImperialSimpleFutureWeatherEntryXU (
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempF")
    override val avgTemp: Double,
    @ColumnInfo(name = "condition_text")
    override val condition: String
) : IUnitSpecificFutureWeatherEntry