package com.github.abhrp.dewpoint.di.module

import com.github.abhrp.dewpoint.BuildConfig
import com.github.abhrp.dewpoint.remote.WeatherApiService
import com.github.abhrp.dewpoint.remote.WeatherApiServiceFactory
import com.github.abhrp.dewpoint.remote.repository.WeatherRepository
import com.github.abhrp.dewpoint.remote.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RemoteModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun providerWeatherApiService(): WeatherApiService {
            return WeatherApiServiceFactory.getWeatherApi(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}