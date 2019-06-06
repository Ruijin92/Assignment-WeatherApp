package com.hva.weather.data.network.XU

import com.hva.weather.data.network.IConnectivityInterceptor
import com.hva.weather.data.db.XU.current.CurrentWeatherResponse
import com.hva.weather.data.db.XU.forecast.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//http://api.apixu.com/v1/current.json?key={KEY}&q=Dornbirn&Lang=en

//API Interface for the WeatherService using the Retrofit, Interceptor, Coroutines and GsonConverter
//With Retrofit a call is started then with the Interceptor i add some extra information to the request
//with the corountines i await till the data class is loaded and then convert it with the gson converter from Json to Gson
interface IXUWeatherApiService {

    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>
    //Deferred is part of the kotlin Coroutins -> means even thought we don't have
    //the data complete we still can give back the DataTyp
    @GET("forecast.json")
    fun getFutureWeather(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") languageCode: String = "en"
    ) : Deferred<FutureWeatherResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: IConnectivityInterceptor): IXUWeatherApiService {
            //the requestInterceptor intercepts the call which we do with the getCurrentWeather()
            //we are adding the API-Key to the request
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", "bac1b0b958094eabab0130436190705")
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
                .baseUrl("https://api.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()) //GSON -> converts the json object which we get from the api to a gson
                .build()
                .create(IXUWeatherApiService::class.java)
        }
    }
}