package com.hva.weather.ui.weather.future.list

import com.hva.weather.R
import com.hva.weather.data.db.XU.MetricSimpleFutureWeatherEntryXU
import com.hva.weather.data.db.forecast.IUnitSpecificFutureWeatherEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class FutureWeatherItem(val weatherEntry: IUnitSpecificFutureWeatherEntry) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = weatherEntry.condition
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun ViewHolder.updateDate() {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(formatter)
    }

    private fun ViewHolder.updateTemperature() {
        val unit = if (weatherEntry is MetricSimpleFutureWeatherEntryXU) "°C" else "°F"
        textView_temperature.text = "${weatherEntry.avgTemp}$unit"
    }
}