package com.github.abhrp.dewpoint.util

import javax.inject.Inject


class WeatherDataUtil @Inject constructor() {

    fun getTemperature(temperature: Double?): String? {
        temperature?.let {
            val degree = "\u2103"
            return "$it$degree"
        }
        return null
    }

    fun getHumidity(humidity: Double?): String? {
        humidity?.let {
            val percentage = it * 100
            return "$percentage%"
        }
        return null
    }

    fun getPressure(pressure: Double?): String? {
        pressure?.let {
            return "$it hPa"
        }
        return null
    }

    fun getWindSpeed(windSpeed: Double?): String? {
        windSpeed?.let {
            return "$it m/secs"
        }
        return null
    }

    fun getCloudCover(cloudCover: Double?): String? {
        cloudCover?.let {
            val percentage = it * 100
            return "$percentage%"
        }
        return null
    }

    fun getVisibility(visibility: Double?): String? {
        visibility?.let {
            return "$it kms"
        }
        return null
    }
}