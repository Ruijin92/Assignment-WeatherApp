package com.hva.weather.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.hva.weather.data.provider.IUnitProvider
import com.hva.weather.data.repository.IWeatherRepository
import com.hva.weather.internal.UnitSystem
import com.hva.weather.internal.lazyDeferred

class CurrentWeatherViewModel(private val repo: IWeatherRepository, private val unitProvider: IUnitProvider) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

     val isMetric: Boolean get() = unitSystem == UnitSystem.METRIC //If unitSystem == UnitySystem.Metric it will return true

    val weather by lazyDeferred {
        repo.getCurrentWeather(isMetric)
    }
}
