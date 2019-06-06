package com.hva.weather.data.db.XU

/**
 * Interface with the namings, because we have two different settings Imperial and Metric
 * With 2 different classes which implements this Interface we Annotate it with data it should have
 **/
interface IUnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}