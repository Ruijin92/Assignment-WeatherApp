package com.hva.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hva.weather.data.db.OAPI.entity.CurrentWeatherResponse
import com.hva.weather.data.db.XU.apixu.entity.CurrentWeatherEntryXU
import com.hva.weather.internal.Converters

@Database(entities = arrayOf(CurrentWeatherEntryXU::class, CurrentWeatherResponse::class), version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): ICurrentWeatherDao

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