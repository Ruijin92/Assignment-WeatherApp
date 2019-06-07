package com.hva.weather.ui.weather.future.list

import com.hva.weather.data.provider.IUnitProvider
import com.hva.weather.data.repository.IWeatherRepository
import com.hva.weather.internal.lazyDeferred
import com.hva.weather.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(private val repo: IWeatherRepository, unitProvider: IUnitProvider) :
    WeatherViewModel(repo, unitProvider) {

    val weatherEntires by lazyDeferred{
        repo.getFutureWeatherList(LocalDate.now(),super.isMetric)
    }
}
