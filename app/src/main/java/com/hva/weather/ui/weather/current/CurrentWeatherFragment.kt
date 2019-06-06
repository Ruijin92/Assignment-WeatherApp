package com.hva.weather.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.hva.weather.R
import com.hva.weather.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindData()
    }

    /**
     * fetching the data from the viewModel with await()
     */
    private fun bindData() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer
            progressBar_loading.visibility = View.GONE

            setLoaction("Dornbirn, Vorarlberg")
            setTempAndFeelLike(it.temperature,it.feelsLikeTemperature)
            setCondition(it.conditionText)
            setPrecipitation(it.precipitationVolume)
            setCondition(it.conditionText)
            setWind(it.windDirection,it.windSpeed)
            setVisibility(it.visibilityDistance)

            //setDuration(it.duration)
        })
    }

    private fun setLoaction(loc: String) {
        location_view.text = loc
    }

    private fun setTempAndFeelLike(temp: Double, feel: Double) {
        val unit = if (viewModel.isMetric) "°C" else "°F"
        degree_view.text = "$temp$unit"
        feelsLike_view.text = "Feels like $feel$unit"
    }

    private fun setCondition(con: String) {
        condtion_view.text = con
    }

    private fun setPrecipitation(pre: Double) {
        val unit = if (viewModel.isMetric) "mm" else "in"
        precipitation_view.text = "$pre $unit"
    }

    private fun setWind(windDir: String, windSpeed: Double) {
        val unit = if (viewModel.isMetric) "kph" else "mph"
        wind_view.text = "$windDir, $windSpeed $unit"

    }

    private fun setVisibility(vis: Double) {
        val unit = if (viewModel.isMetric) "km" else "mi"
        visibility_view.text = "$vis $unit"
    }

    private fun setDuration(dur: String) {
        val unit = "h"
        duration_view.text = "$dur$unit"
    }
}
