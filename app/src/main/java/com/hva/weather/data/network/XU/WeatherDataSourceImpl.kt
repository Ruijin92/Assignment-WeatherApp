package com.hva.weather.data.network.XU

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hva.weather.data.db.XU.entity.CurrentWeatherResponse
import com.hva.weather.internal.NoConnectivityException

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
}
