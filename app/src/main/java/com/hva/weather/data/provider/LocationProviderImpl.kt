package com.hva.weather.data.provider

import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation

class LocationProviderImpl : ILocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Dornbirn"
    }
}