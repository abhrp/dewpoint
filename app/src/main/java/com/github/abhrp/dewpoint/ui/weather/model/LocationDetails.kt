package com.github.abhrp.dewpoint.ui.weather.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationDetails(val feature: String?, val subLocality: String?, val locality: String?, val country: String?)