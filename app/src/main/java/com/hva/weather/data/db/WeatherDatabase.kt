package com.hva.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hva.weather.data.db.OAPI.entity.CurrentWeatherResponse
import com.hva.weather.data.db.XU.apixu.entity.CurrentWeatherEntryXU
import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation
import com.hva.weather.data.db.XU.forecast.FutureWeatherEntire
import com.hva.weather.data.db.XU.forecast.FutureWeatherResponse
import com.hva.weather.internal.Converters

@Database(entities = [CurrentWeatherEntryXU::class, CurrentWeatherResponse::class, WeatherLocation::class, FutureWeatherEntire::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): ICurrentWeatherDao
    abstract fun weatherLocationDao(): IWeatherLocationDao
    abstract fun futureWeatherDao(): IFutureWeatherDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null //@Volatile because of the thread access
        private val LOCK = Any() //Dummy object that secures that if two threads are doing the same thing -> LOCK

        //Checks if the database is already instantiate, if not it builds it
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java, "weather.db"
            ).build()
    }
}