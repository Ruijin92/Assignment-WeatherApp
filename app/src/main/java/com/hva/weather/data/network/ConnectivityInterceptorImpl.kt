package com.hva.weather.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.hva.weather.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Propose of this class is that the app doesnt crash when there is no internet connection
 */
class ConnectivityInterceptorImpl(context: Context) : IConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline()){
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

    /**
     * In this function we look it the device is online or not
     */
    private fun isOnline(): Boolean{
        val connectivityHandler = appContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityHandler.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}