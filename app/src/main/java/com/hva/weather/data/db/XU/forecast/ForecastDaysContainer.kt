package com.hva.weather.data.db.XU.forecast

import com.google.gson.annotations.SerializedName


data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntire>
)