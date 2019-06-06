package com.hva.weather.data.provider

import com.hva.weather.internal.UnitSystem


interface IUnitProvider{
    fun getUnitSystem(): UnitSystem
}