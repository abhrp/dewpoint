package com.github.abhrp.dewpoint.ui.weather

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.github.abhrp.dewpoint.R
import com.github.abhrp.dewpoint.ui.weather.model.LocationDetails
import com.github.abhrp.dewpoint.ui.weather.model.Weather
import com.github.abhrp.dewpoint.util.NetworkUtil
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.weather_card.*
import permissions.dispatcher.*
import javax.inject.Inject

@RuntimePermissions
class WeatherActivity : DaggerAppCompatActivity(), WeatherContract.WeatherView {

    @Inject
    lateinit var weatherPresenter: WeatherContract.WeatherPresenter

    @Inject
    lateinit var networkUtil: NetworkUtil

    private val REQUEST_LOCATION_PERMISSION = 1093
    private val LOCATION_INTERVAL: Long = 10 * 60 * 1000

    private lateinit var locationManager: LocationManager

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            location?.let { loc ->
                fetchWeatherData(loc.latitude, loc.longitude)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            Log.i(WeatherActivity::class.qualifiedName, provider)
        }

        override fun onProviderEnabled(provider: String?) {
            Log.i(WeatherActivity::class.qualifiedName, provider)
        }

        override fun onProviderDisabled(provider: String?) {
            Log.i(WeatherActivity::class.qualifiedName, provider)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
        setContentView(R.layout.activity_weather)
        supportActionBar?.let {
            it.hide()
        }
        weatherPresenter.attach(this)
        weatherPresenter.getCachedWeatherData()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        if (!networkUtil.isConnected()) {
            showError(error = getString(R.string.no_internet))
            weatherPresenter.getCachedWeatherData()
        } else {
            getUserLocationDetailsWithPermissionCheck()
        }
    }


    private fun fetchWeatherData(latitude: Double, longitude: Double) {
        weatherPresenter.getForecast(latitude, longitude)
        weatherPresenter.getLocationDetails(latitude, longitude)
    }


    override fun showWeatherData(weather: Weather) {
        var temp: String? = null

        date.text = weather.date

        weather.temperature?.let {
            temp = it
            temperature.text = it
        }

        weatherIcon.setImageResource(weather.icon)

        weather.feelsLike?.let {
            if (temp != null && !temp.equals(it)) {
                feelsLike.text = getString(R.string.feels_like, it)
            }
        }
        weather.summary?.let {
            summary.text = it
        }

        weather.dew?.let {
            dew.text = getString(R.string.dew_point, it)
        }

        weather.humidity?.let {
            humidity.text = getString(R.string.humidity, it)
        }

        weather.pressure?.let {
            pressure.text = getString(R.string.pressure, it)
        }

        weather.windSpeed?.let {
            windSpeed.text = getString(R.string.wind_speed, it)
        }

        weather.cloudCover?.let {
            cloudCover.text = getString(R.string.cloud_cover, it)
        }

        weather.visibility?.let {
            visibility.text = getString(R.string.visibility, it)
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun getUserLocationDetails() {
        val locationProvider =
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER
        try {
            useLastKnownLocation()
            locationManager.requestLocationUpdates(locationProvider, LOCATION_INTERVAL, 0f, locationListener)
        } catch (e: SecurityException) {
            showError(error = getString(R.string.no_location))
            Log.e(WeatherActivity::class.qualifiedName, "Error : ", e)
        }
    }

    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForLocation(request: PermissionRequest) {
        showRationaleDialog(R.string.rationale, request)
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onLocationPermissionDenied() {
        showError(error = getString(R.string.no_permission))
        weatherPresenter.getWeatherFromCachedLocation()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onLocationNeverAsk() {
        showError(error = getString(R.string.no_permission))
        weatherPresenter.getWeatherFromCachedLocation()
    }

    private fun useLastKnownLocation() {
        val location = getLastKnownLocation()
        if (location != null) {
            fetchWeatherData(location.latitude, location.longitude)
        } else {
            weatherPresenter.getWeatherFromCachedLocation()
        }
    }

    @Throws(SecurityException::class)
    private fun getLastKnownLocation(): Location? {
        val providers = locationManager.getProviders(true)
        var location: Location? = null
        for (provider in providers) {
            val l = locationManager.getLastKnownLocation(provider)
            if (l != null) {
                if (location == null || l.accuracy < location.accuracy) {
                    location = l
                }
            }
        }
        return location
    }

    override fun showLocationData(locationDetails: LocationDetails) {
        var properLocation = mutableListOf<String>()
        locationDetails.feature?.let {
            properLocation.add(it)
        }
        locationDetails.subLocality?.let {
            properLocation.add(it)
        }
        locationDetails.locality?.let {
            properLocation.add(it)
        }
        locationDetails.country?.let {
            properLocation.add(it)
        }
        location.text = properLocation.joinToString(", ")
    }

    override fun showProgressLoader() {

    }

    override fun hideProgressLoader() {

    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setPositiveButton(R.string.button_allow) { _, _ -> request.proceed() }
            .setNegativeButton(R.string.button_deny) { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(messageResId)
            .show()
    }

    override fun onDestroy() {
        locationManager.removeUpdates(locationListener)
        super.onDestroy()
    }

}
