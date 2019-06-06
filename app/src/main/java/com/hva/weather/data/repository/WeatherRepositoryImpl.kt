package com.hva.weather.data.repository

import androidx.lifecycle.LiveData
import com.hva.weather.data.db.ICurrentWeatherDao
import com.hva.weather.data.db.IWeatherLocationDao
import com.hva.weather.data.db.XU.IUnitSpecificCurrentWeatherEntry
import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation
import com.hva.weather.data.db.XU.current.CurrentWeatherResponse
import com.hva.weather.data.network.XU.IWeatherDataSource
import com.hva.weather.data.provider.ILocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

/**
 * Handel's all the persisting and storing of the data
 */
class WeatherRepositoryImpl(
    private val currentWeatherDao: ICurrentWeatherDao,
    private val weatherLocationDao: IWeatherLocationDao,
    private val weatherDataSourceXU: IWeatherDataSource,
    private val locationProvider: ILocationProvider
) : IWeatherRepository {

    /**
     * Here we get the current location which is safed in the database we need it to check if it is changed
     */
    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    /**
     * Here we observe the downloadedCurrentWeather, we observer it forever so each time there is new data then
     * it will persist it into our database and also we can use oberserforever here because we not on a UI class so
     * would not hurt any lifecycle or UI-thread of the application
     */
    init {
        //TODO: Add the other API: downloadedCurrentWeather here
        weatherDataSourceXU.downloadedCurrentWeather.observeForever {
            persistFetchedCurrentWeather(it)
        }
    }

    /**
     * Here we get the persisted data out of the database. Couroutine provides a function called withContext, that returns
     * a something in this case its either a metric weather or a imperial weather
     */
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out IUnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            //TODO: check the Settings which API should be used
            if (true) {
                return@withContext if (metric) currentWeatherDao.getWeatherMetric() else currentWeatherDao.getWeatherImperial()
            } else {
                return@withContext if (metric) currentWeatherDao.getWeatherMetric() else currentWeatherDao.getWeatherImperial()
            }
        }
    }

    /**
     * Upserts the currentWeather response into the database with a coroutine
     * Here you can launch a GlobalScope couroutine because its not a Fragment or a Activity -> doesnt hurt (Lifecyle)
     */
    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntryXU)
            weatherLocationDao.upsert(fetchedWeather.location)
            //TODO: add the other API upsert into here
        }
    }

    /**
     * Checks if its needed to fetch if it does it calls the fetchcurrentweather function and gets the data
     */
    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }

        if (isFetchedCurrentNeeded(lastWeatherLocation.zonedDateTime)) {
                fetchCurrentWeather()
            }
    }

    /**
     * Makes an API call and gets the current weather
     */
    private suspend fun fetchCurrentWeather() {
        weatherDataSourceXU.fetchCurrentWeather(locationProvider.getPreferredLocationString(), "en")
        //TODO: add the other api call here
    }

    /**
     * This function checks if new data from the network is needed. If more then 30mins passes it will return true
     */
    private fun isFetchedCurrentNeeded(lastTimeFetched: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastTimeFetched.isBefore(thirtyMinutesAgo)
    }
}