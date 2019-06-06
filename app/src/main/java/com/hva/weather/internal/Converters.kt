package com.hva.weather.internal

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hva.weather.data.db.OAPI.entity.Weather

class Converters {

    @TypeConverter
    fun listToJson(value: List<Weather>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Weather>? {

        val objects = Gson().fromJson(value, Array<Weather>::class.java) as Array<Weather>
        return objects.toList()
    }
}