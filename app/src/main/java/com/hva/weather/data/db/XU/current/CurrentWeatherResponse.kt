package com.hva.weather.data.db.XU.current

import com.google.gson.annotations.SerializedName
import com.hva.weather.data.db.XU.apixu.entity.CurrentWeatherEntryXU
import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation

data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntryXU: CurrentWeatherEntryXU
)