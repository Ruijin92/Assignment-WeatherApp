package com.hva.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hva.weather.data.db.XU.apixu.entity.CURRENT_WEATHER_ID
import com.hva.weather.data.db.XU.apixu.entity.CurrentWeatherEntryXU

import com.hva.weather.data.db.XU.ImperialCurrentWeatherEntryXU
import com.hva.weather.data.db.XU.MetricCurrentWeatherEntryXU

//TODO: change names to getWeatherMetricAPIx

@Dao
interface ICurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntryXU: CurrentWeatherEntryXU)

    @Query("SELECT * from current_weather_xu where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntryXU>

    @Query("SELECT * from current_weather_xu where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntryXU>
}