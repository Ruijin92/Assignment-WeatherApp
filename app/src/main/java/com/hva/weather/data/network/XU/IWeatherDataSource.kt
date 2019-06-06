package com.hva.weather.data.network.XU

import androidx.lifecycle.LiveData
import com.hva.weather.data.db.XU.current.CurrentWeatherResponse
import com.hva.weather.data.db.XU.forecast.FutureWeatherResponse

interface IWeatherDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    )
}