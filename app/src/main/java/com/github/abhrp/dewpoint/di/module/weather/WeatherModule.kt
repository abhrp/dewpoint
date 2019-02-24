package com.github.abhrp.dewpoint.di.module.weather

import com.github.abhrp.dewpoint.ui.weather.WeatherContract
import com.github.abhrp.dewpoint.ui.weather.WeatherPresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class WeatherModule {
    @Binds
    abstract fun bindPresenter(weatherPresenterImpl: WeatherPresenterImpl): WeatherContract.WeatherPresenter
}