package com.hva.weather.data.network.OAPI

import androidx.lifecycle.LiveData
import com.hva.weather.data.db.OAPI.entity.CurrentWeatherResponse

interface IWeatherDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    /**
     * This function updates the current downloaded weather
     */
    suspend fun fetchCurrentWeather(
        location: String
    )
}