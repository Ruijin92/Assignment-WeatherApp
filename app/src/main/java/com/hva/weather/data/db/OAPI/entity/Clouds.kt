package com.hva.weather.data.db.OAPI.entity


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)