package com.hva.weather.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.hva.weather.data.provider.IUnitProvider
import com.hva.weather.data.repository.IWeatherRepository
import com.hva.weather.data.repository.WeatherRepositoryImpl
import com.hva.weather.internal.UnitSystem
import com.hva.weather.internal.lazyDeferred
import com.hva.weather.ui.base.WeatherViewModel

class CurrentWeatherViewModel(private val repo: IWeatherRepository, private val unitProvider: IUnitProvider) : WeatherViewModel(repo, unitProvider) {

    val weather by lazyDeferred {
        repo.getCurrentWeather(super.isMetric)
    }
}
