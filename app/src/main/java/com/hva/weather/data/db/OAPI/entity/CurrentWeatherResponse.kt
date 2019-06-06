package com.hva.weather.data.db.OAPI.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather_op")
data class CurrentWeatherResponse(
    @SerializedName("base")
    val base: String,
    //@SerializedName("clouds")
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int,
    //@SerializedName("coord")
    @Embedded(prefix = "coord_")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("id")
    val id: Int,
    //@SerializedName("main")
    @Embedded(prefix = "main_")
    val main: Main,
    @SerializedName("name")
    val name: String,
    //@SerializedName("sys")
    @Embedded(prefix = "sys_")
    val sys: Sys,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    //@SerializedName("wind")
    @Embedded(prefix = "wind_")
    val wind: Wind

) {
    @PrimaryKey(autoGenerate = false)
    var ib: Int = CURRENT_WEATHER_ID
}