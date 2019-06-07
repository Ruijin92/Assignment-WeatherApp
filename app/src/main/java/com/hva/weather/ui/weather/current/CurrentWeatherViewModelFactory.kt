package com.hva.weather.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hva.weather.data.provider.IUnitProvider
import com.hva.weather.data.repository.IWeatherRepository

@Suppress("UNCHECKED_CAST")
class CurrentWeatherViewModelFactory(private val repo: IWeatherRepository, private val unitProvider: IUnitProvider) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(repo, unitProvider) as T
    }
}