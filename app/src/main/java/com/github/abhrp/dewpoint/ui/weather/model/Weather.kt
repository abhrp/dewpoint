package com.github.abhrp.dewpoint.ui.weather.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    val date: String,
    val temperature: String?,
    val summary: String?,
    val feelsLike: String?,
    val icon: Int,
    val dew: String?,
    val humidity: String?,
    val pressure: String?,
    val windSpeed: String?,
    val cloudCover: String?,
    val visibility: String?
)