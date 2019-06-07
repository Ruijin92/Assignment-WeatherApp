package com.hva.weather.data.repository

import androidx.lifecycle.LiveData
import com.hva.weather.data.db.XU.IUnitSpecificCurrentWeatherEntry
import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation
import com.hva.weather.data.db.forecast.IUnitSpecificFutureWeatherEntry
import org.threeten.bp.LocalDate

interface IWeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out IUnitSpecificCurrentWeatherEntry>
    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<IUnitSpecificFutureWeatherEntry>>

    suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<out IUnitSpecificFutureWeatherEntry>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}