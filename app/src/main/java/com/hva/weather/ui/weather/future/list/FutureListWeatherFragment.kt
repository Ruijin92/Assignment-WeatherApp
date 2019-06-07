package com.hva.weather.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.hva.weather.R
import com.hva.weather.data.db.forecast.IUnitSpecificFutureWeatherEntry
import com.hva.weather.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)
        bindData()
    }

    private fun bindData() = launch(Dispatchers.Main) {
        val futureweatherEntire = viewModel.weatherEntires.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@FutureListWeatherFragment, Observer {
            if(it == null) return@Observer
            setLocation(it.name)
        })

        futureweatherEntire.observe(this@FutureListWeatherFragment, Observer {
            if(it == null) return@Observer
            progressBar_loading.visibility = View.GONE
            initRecyclerView(it.toFutureWeatherItem())
        })
    }

    private fun setLocation(name: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = name
        //(activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next Week"
    }

    /**
     * Transforms the UnitSpecific entry into a FutureWeatherItem
     */
    private fun List<IUnitSpecificFutureWeatherEntry>.toFutureWeatherItem() : List<FutureWeatherItem> {
        return this.map{
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply{
            addAll(items)
        }
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }
    }
}
