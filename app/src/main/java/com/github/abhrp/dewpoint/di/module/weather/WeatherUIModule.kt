package com.github.abhrp.dewpoint.di.module.weather

import com.github.abhrp.dewpoint.di.annotation.ActivityScope
import com.github.abhrp.dewpoint.ui.weather.WeatherActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module()
abstract class WeatherUIModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [WeatherModule::class])
    abstract fun contributesWeatherActivity(): WeatherActivity
}