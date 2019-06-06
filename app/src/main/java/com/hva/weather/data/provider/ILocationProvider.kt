package com.hva.weather.data.provider

import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation

interface ILocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}