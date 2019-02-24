package com.github.abhrp.dewpoint.remote.repository

import com.github.abhrp.dewpoint.remote.WeatherData
import io.reactivex.Single

interface WeatherRepository {
    fun getForecast(latitude: Double, longitude: Double): Single<WeatherData>
}