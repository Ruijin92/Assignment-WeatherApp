package com.hva.weather.data.network.XU

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hva.weather.data.db.XU.current.CurrentWeatherResponse
import com.hva.weather.data.db.XU.forecast.FutureWeatherResponse
import com.hva.weather.internal.NoConnectivityException

private const val FORECAST_DAYS = 7
class WeatherDataSourceImpl(private val apiService: IXUWeatherApiService) : IWeatherDataSource {


    //Because LiveData cannot be changed or updated we need a MutableLiveData
    private val _downloadedCurrentWeather: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse> get() = _downloadedCurrentWeather  //cast to LiveData

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val currentWeather = apiService.getCurrentWeather(location, languageCode).await()
            _downloadedCurrentWeather.postValue(currentWeather)
        } catch (e: NoConnectivityException) {
            //We just log the exception so the program can go on if there is no connection
            Log.d("Connectivity", "No Internet connections", e)
        }
    }

    //Because LiveData cannot be changed or updated we need a MutableLiveData
    private val _downloadedFutureWeather: MutableLiveData<FutureWeatherResponse> = MutableLiveData()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse> get() = _downloadedFutureWeather  //cast to LiveData

    override suspend fun fetchFutureWeather(location: String, languageCode: String) {
        try {
            val futureWeather = apiService.getFutureWeather(location, FORECAST_DAYS, languageCode).await()
            _downloadedFutureWeather.postValue(futureWeather)
        } catch (e: NoConnectivityException) {
            //We just log the exception so the program can go on if there is no connection
            Log.d("Connectivity", "No Internet connections", e)
        }
    }
}
