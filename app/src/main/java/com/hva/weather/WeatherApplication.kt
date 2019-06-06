package com.hva.weather

import android.app.Application
import com.hva.weather.data.db.WeatherDatabase
import com.hva.weather.data.network.ConnectivityInterceptorImpl
import com.hva.weather.data.network.IConnectivityInterceptor
import com.hva.weather.data.network.XU.IWeatherDataSource
import com.hva.weather.data.network.XU.IXUWeatherApiService
import com.hva.weather.data.network.XU.WeatherDataSourceImpl
import com.hva.weather.data.provider.IUnitProvider
import com.hva.weather.data.provider.UnitProviderImpl
import com.hva.weather.data.repository.IWeatherRepository
import com.hva.weather.data.repository.WeatherRepositoryImpl
import com.hva.weather.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Manages all the dependencies of the app
 */
class WeatherApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        //androidXModule provides contexts and services of the application so that you can call instance() when something is needed
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }

        bind<IConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { IXUWeatherApiService(instance()) }

        bind<IWeatherDataSource>() with singleton { WeatherDataSourceImpl(instance()) }
        bind<IWeatherRepository>() with singleton { WeatherRepositoryImpl(instance(), instance()) }
        bind<IUnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    /**
     * Overriding the onCreate() from Application to add some additional stuff for the startup
     * Added the Time Library (AndroidThreeTen) here
     */
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}

