package com.hva.weather.data.repository

import androidx.lifecycle.LiveData
import com.hva.weather.data.db.XU.IUnitSpecificCurrentWeatherEntry

interface IWeatherRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out IUnitSpecificCurrentWeatherEntry>
}