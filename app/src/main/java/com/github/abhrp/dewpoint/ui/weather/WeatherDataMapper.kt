package com.github.abhrp.dewpoint.ui.weather

import com.github.abhrp.dewpoint.remote.WeatherData
import com.github.abhrp.dewpoint.ui.base.ViewMapper
import com.github.abhrp.dewpoint.ui.weather.model.Weather
import com.github.abhrp.dewpoint.util.DateUtil
import com.github.abhrp.dewpoint.util.IconUtil
import com.github.abhrp.dewpoint.util.WeatherDataUtil
import javax.inject.Inject

class WeatherDataMapper @Inject constructor(
    private val dateUtil: DateUtil,
    private val iconUtil: IconUtil,
    private val weatherDataUtil: WeatherDataUtil
) : ViewMapper<WeatherData, Weather> {
    override fun mapToView(model: WeatherData): Weather {
        val currentWeatherData = model.currentWeatherData
        return Weather(
            dateUtil.getFormattedDate(currentWeatherData.time),
            weatherDataUtil.getTemperature(currentWeatherData.temperature),
            currentWeatherData.summary,
            weatherDataUtil.getTemperature(currentWeatherData.feelsLike),
            iconUtil.getWeatherIcon(currentWeatherData.icon),
            weatherDataUtil.getTemperature(currentWeatherData.dew),
            weatherDataUtil.getHumidity(currentWeatherData.humidity),
            weatherDataUtil.getPressure(currentWeatherData.pressure),
            weatherDataUtil.getWindSpeed(currentWeatherData.windSpeed),
            weatherDataUtil.getCloudCover(currentWeatherData.cloudCover),
            weatherDataUtil.getVisibility(currentWeatherData.visibility)
        )
    }
}