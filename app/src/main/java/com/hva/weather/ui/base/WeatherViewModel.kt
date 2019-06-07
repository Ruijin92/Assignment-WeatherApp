package com.hva.weather.ui.base

import androidx.lifecycle.ViewModel
import com.hva.weather.data.provider.IUnitProvider
import com.hva.weather.data.repository.IWeatherRepository
import com.hva.weather.internal.UnitSystem
import com.hva.weather.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: IWeatherRepository, unitProvider: IUnitProvider) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}