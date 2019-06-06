package com.hva.weather.data.network.OAPI

import com.hva.weather.data.network.IConnectivityInterceptor
import com.hva.weather.data.db.OAPI.entity.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//api.openweathermap.org/data/2.5/weather?q=Dornbirn&APPID={KEY}

//API Interface for the WeatherService using the Retrofit, Interceptor, Coroutines and GsonConverter
//With Retrofit a call is started then with the Interceptor i add some extra information to the request
//with the corountines i await till the data class is loaded and then convert it with the gson converter from Json to Gson
interface IOAPIWeatherApiService {

    @GET("weather")
    fun getCurrentWeather(@Query("q") location: String): Deferred<CurrentWeatherResponse>
    //Deferred is part of the kotlin Coroutins -> means even thought we don't have
    //the data complete we still can give back the DataTyp

    companion object {
        operator fun invoke(connectivityInterceptor: IConnectivityInterceptor): IOAPIWeatherApiService {
            //the requestInterceptor intercepts the call which we do with the getCurrentWeather()
            //we are adding the API-Key to the request
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("APPID", "6b2280c48d1219c8cdb9dec4dce68056")
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()) //GSON -> converts the json object which we get from the api to a gson
                .build()
                .create(IOAPIWeatherApiService::class.java)
        }
    }
}