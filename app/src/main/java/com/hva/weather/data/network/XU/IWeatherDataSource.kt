package com.hva.weather.data.network.XU

import androidx.lifecycle.LiveData
import com.hva.weather.data.db.XU.entity.CurrentWeatherResponse

interface IWeatherDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    /**
     * This function updates the current downloaded weather
     */
    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}