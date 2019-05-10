package com.github.abhrp.dewpoint.util

import com.github.abhrp.dewpoint.R
import javax.inject.Inject


class IconUtil @Inject constructor() {

    fun getWeatherIcon(icon: String?): Int {
        return when (icon) {
            "clear-day" -> R.drawable.ic_clear_day
            "clear-night" -> R.drawable.ic_clear_night
            "rain" -> R.drawable.ic_rain
            "snow" -> R.drawable.ic_snow
            "wind" -> R.drawable.ic_wind
            "cloudy" -> R.drawable.ic_cloudy
            "partly-cloudy-day" -> R.drawable.ic_cloudy_day
            "partly-cloudy-night" -> R.drawable.ic_cloudy_night
            else -> R.drawable.ic_weather
        }
    }
}