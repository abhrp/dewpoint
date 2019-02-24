package com.github.abhrp.dewpoint.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherData(
    val time: Long,
    val summary: String?,
    val icon: String?,
    val temperature: Double?,
    @Json(name = "apparentTemperature") val feelsLike: Double?,
    @Json(name = "dewPoint") val dew: Double?,
    val humidity: Double?,
    val pressure: Double?,
    val windSpeed: Double?,
    val cloudCover: Double?,
    val visibility: Double?
)