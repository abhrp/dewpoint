package com.github.abhrp.dewpoint.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherData(
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    @Json(name = "currently") val currentWeatherData: CurrentWeatherData
)