package com.github.abhrp.dewpoint.remote.repository

import com.github.abhrp.dewpoint.remote.WeatherApiService
import com.github.abhrp.dewpoint.remote.WeatherData
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherApiService) : WeatherRepository {


    override fun getForecast(latitude: Double, longitude: Double): Single<WeatherData> {
        val location = "$latitude,$longitude"
        return weatherApiService.getForecast(location)
    }

}