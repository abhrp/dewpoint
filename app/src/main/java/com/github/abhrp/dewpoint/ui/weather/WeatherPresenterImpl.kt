package com.github.abhrp.dewpoint.ui.weather

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.github.abhrp.dewpoint.local.WeatherSharedPreferences
import com.github.abhrp.dewpoint.remote.repository.WeatherRepository
import com.github.abhrp.dewpoint.ui.weather.model.LocationDetails
import com.github.abhrp.dewpoint.ui.weather.model.Weather
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class WeatherPresenterImpl @Inject constructor(
    private val context: Context,
    private val weatherRepository: WeatherRepository,
    private val mapper: WeatherDataMapper,
    private val weatherSharedPreferences: WeatherSharedPreferences
) : WeatherContract.WeatherPresenter {


    private val subscriptions = CompositeDisposable()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var weatherView: WeatherContract.WeatherView

    override fun getForecast(latitude: Double, longitude: Double) {
        weatherView.showProgressLoader()

        this.latitude = latitude
        this.longitude = longitude
        weatherSharedPreferences.setLatitude(latitude)
        weatherSharedPreferences.setLongitude(longitude)

        fetchForecast(latitude, longitude)
    }

    override fun getLocationDetails(latitude: Double, longitude: Double) {
        val disposable = Single.just(Geocoder(context, Locale.getDefault()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.getFromLocation(latitude, longitude, 1)
            }
            .map {
                val address = getAddress(it)
                address?.let { a ->
                    weatherSharedPreferences.setLocationDetails(a)
                }
                address
            }
            .subscribe { locationDetails ->
                locationDetails?.let {
                    weatherView.showLocationData(it)
                }
            }
        addDisposable(disposable)
    }

    private fun getAddress(addresses: List<Address?>): LocationDetails? {
        if (!addresses.isEmpty()) {
            val address = addresses[0]
            address?.let { a ->
                return LocationDetails(a.featureName, a.subLocality, a.locality, a.countryName)
            }
        }
        return null
    }

    override fun refreshWeatherData() {
        weatherView.showProgressLoader()
        fetchForecast(latitude, longitude)
    }

    override fun getWeatherFromCachedLocation() {
        val latitude = weatherSharedPreferences.getLatitude()
        val longitude = weatherSharedPreferences.getLongtitude()
        if (latitude != null && longitude != null) {
            fetchForecast(latitude, longitude)
            getLocationDetails(latitude, longitude)
        }
    }

    override fun getCachedWeatherData() {
        val weather = weatherSharedPreferences.getWeatherViewData()
        val locationDetails = weatherSharedPreferences.getLocationDetails()
        weather?.let {
            weatherView.showWeatherData(it)
        }
        locationDetails?.let {
            weatherView.showLocationData(it)
        }
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: WeatherContract.WeatherView) {
        this.weatherView = view
    }

    private fun addDisposable(disposable: Disposable) {
        subscriptions.add(disposable)
    }

    private fun fetchForecast(latitude: Double, longitude: Double) {
        val disposable = weatherRepository.getForecast(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val weather = mapper.mapToView(it)
                weatherSharedPreferences.setWeatherViewData(weather)
                weather
            }
            .subscribeWith(WeatherSubscriber())
        addDisposable(disposable)
    }

    private inner class WeatherSubscriber : DisposableSingleObserver<Weather>() {

        override fun onSuccess(t: Weather) {
            weatherView.showWeatherData(t)
        }

        override fun onError(e: Throwable) {
            weatherView.showError("Something went wrong.")
        }

    }

}