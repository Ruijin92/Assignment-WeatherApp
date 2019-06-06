package com.hva.weather.data.repository

import androidx.lifecycle.LiveData
import com.hva.weather.data.db.XU.IUnitSpecificCurrentWeatherEntry
import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation

interface IWeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out IUnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}