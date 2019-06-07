package com.hva.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hva.weather.data.db.XU.ImperialSimpleFutureWeatherEntryXU
import com.hva.weather.data.db.XU.MetricSimpleFutureWeatherEntryXU
import com.hva.weather.data.db.XU.forecast.FutureWeatherEntire
import org.threeten.bp.LocalDate

@Dao
interface IFutureWeatherDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntire>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(futureWeatherEntryXU: List<FutureWeatherEntire>)

    @Query("SELECT * from future_weather where date(date) >= date(:startDate)")
    fun getFutureWeatherMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntryXU>>

    @Query("SELECT * from future_weather where date(date) >= date(:startDate)")
    fun getFutureWeatherImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntryXU>>

    @Query("SELECT count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate) : Int

    @Query("DELETE from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)
}