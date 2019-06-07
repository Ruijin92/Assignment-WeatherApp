package com.hva.weather.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hva.weather.data.provider.IUnitProvider
import com.hva.weather.data.repository.IWeatherRepository

class FutureListWeatherViewModelFactory(
    private val forecastRepository: IWeatherRepository,
    private val unitProvider: IUnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(
            forecastRepository,
            unitProvider
        ) as T
    }
}