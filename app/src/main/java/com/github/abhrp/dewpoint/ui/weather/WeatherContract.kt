package com.github.abhrp.dewpoint.ui.weather

import com.github.abhrp.dewpoint.ui.base.BaseContract
import com.github.abhrp.dewpoint.ui.weather.model.LocationDetails
import com.github.abhrp.dewpoint.ui.weather.model.Weather

class WeatherContract {

    interface WeatherView : BaseContract.BaseView {
        fun showWeatherData(weather: Weather)
        fun showLocationData(locationDetails: LocationDetails)
    }

    interface WeatherPresenter : BaseContract.BasePresenter<WeatherView> {
        fun getForecast(latitude: Double, longitude: Double)
        fun getLocationDetails(latitude: Double, longitude: Double)
        fun getWeatherFromCachedLocation()
        fun getCachedWeatherData()
        fun refreshWeatherData()
    }
}