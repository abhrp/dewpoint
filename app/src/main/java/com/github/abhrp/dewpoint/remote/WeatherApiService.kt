package com.github.abhrp.dewpoint.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApiService {
    @GET("forecast/${APIConstants.SECRET_KEY}/{location}?units=si&exclude=daily,hourly,flags")
    fun getForecast(@Path("location") location: String): Single<WeatherData>
}