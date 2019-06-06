package com.hva.weather.ui.weather.map

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hva.weather.R

class CurrentWeatherMapFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentWeatherMapFragment()
    }

    private lateinit var viewModel: CurrentWeatherMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_map_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherMapViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
