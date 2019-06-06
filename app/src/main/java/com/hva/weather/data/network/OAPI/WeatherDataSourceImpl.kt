package com.hva.weather.data.network.OAPI

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hva.weather.data.db.OAPI.entity.CurrentWeatherResponse
import com.hva.weather.internal.NoConnectivityException

class WeatherDataSourceImpl(private val apiService: IOAPIWeatherApiService) : IWeatherDataSource {

    //Because LiveData cannot be changed or updated we need a MutableLiveData
    private val _downloadedCurrentWeather: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse> get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val currentWeather = apiService.getCurrentWeather(location).await()
            _downloadedCurrentWeather.postValue(currentWeather)
        } catch (e: NoConnectivityException) {
            //We just log the exception so the program can go on if there is no connection
            Log.e("Connectivity", "No Internet connections", e)
        }
    }
}