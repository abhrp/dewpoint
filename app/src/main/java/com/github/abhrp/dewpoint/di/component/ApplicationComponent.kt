package com.github.abhrp.dewpoint.di.component

import android.app.Application
import com.github.abhrp.dewpoint.WeatherApp
import com.github.abhrp.dewpoint.di.module.ApplicationModule
import com.github.abhrp.dewpoint.di.module.RemoteModule
import com.github.abhrp.dewpoint.di.module.weather.WeatherUIModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, RemoteModule::class, WeatherUIModule::class])
interface ApplicationComponent : AndroidInjector<WeatherApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(app: WeatherApp)
}