package com.github.abhrp.dewpoint.remote

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object WeatherApiServiceFactory {

    fun getWeatherApi(isDebug: Boolean): WeatherApiService {
        val loggingInterceptor = makeHttpLoggingInterceptor(isDebug)
        val okHttpClient = makeOkHttpClient(loggingInterceptor)
        return makeWeatherApiService(okHttpClient)
    }

    private fun makeWeatherApiService(okHttpClient: OkHttpClient): WeatherApiService {
        val retrofit = Retrofit.Builder().baseUrl("https://api.darksky.net/").client(okHttpClient).addConverterFactory(
            MoshiConverterFactory.create()
        ).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(WeatherApiService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).connectTimeout(120, TimeUnit.SECONDS).readTimeout(
            120,
            TimeUnit.SECONDS
        ).build()

    private fun makeHttpLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }
}