package com.hva.weather.data.db.XU.forecast

import com.google.gson.annotations.SerializedName
import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation


data class FutureWeatherResponse(
    @SerializedName("forecast")
    val futureWeather: ForecastDaysContainer,
    val weatherLocation: WeatherLocation
)