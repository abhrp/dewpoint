package com.github.abhrp.dewpoint.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.abhrp.dewpoint.ui.weather.model.LocationDetails
import com.github.abhrp.dewpoint.ui.weather.model.Weather
import com.github.abhrp.dewpoint.util.MoshiProvider
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherSharedPreferences @Inject constructor(context: Context, private val moshiProvider: MoshiProvider) {
    companion object {
        private const val PREF_NAME = "com.github.abhrp.dewpoint.sharedpreferences"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
        private const val LOCATION_DETAILS = "location_details"
        private const val WEATHER_DATA = "weather"
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setLatitude(latitude: Double) {
        sharedPreferences.edit {
            putString(LATITUDE, latitude.toString())
        }
    }

    fun getLatitude(): Double? {
        val lat = sharedPreferences.getString(LATITUDE, null)
        lat?.let {
            return it.toDoubleOrNull()
        }
        return null
    }

    fun setLongitude(longtitude: Double) {
        sharedPreferences.edit {
            putString(LONGITUDE, longtitude.toString())
        }
    }

    fun getLongtitude(): Double? {
        val lon = sharedPreferences.getString(LONGITUDE, null)
        lon?.let {
            return it.toDoubleOrNull()
        }
        return null
    }

    fun setLocationDetails(locationDetails: LocationDetails) {
        val jsonAdapter: JsonAdapter<LocationDetails> = moshiProvider.getMoshi().adapter(LocationDetails::class.java)
        sharedPreferences.edit {
            putString(LOCATION_DETAILS, jsonAdapter.toJson(locationDetails))
        }
    }

    fun getLocationDetails(): LocationDetails? {
        val locationString = sharedPreferences.getString(LOCATION_DETAILS, null)
        locationString?.let {
            val jsonAdapter: JsonAdapter<LocationDetails> =
                moshiProvider.getMoshi().adapter(LocationDetails::class.java)
            return jsonAdapter.fromJson(it)
        }
        return null
    }

    fun setWeatherViewData(weather: Weather) {
        val jsonAdapter: JsonAdapter<Weather> = moshiProvider.getMoshi().adapter(Weather::class.java)
        sharedPreferences.edit {
            putString(WEATHER_DATA, jsonAdapter.toJson(weather))
        }
    }

    fun getWeatherViewData(): Weather? {
        val weatherString = sharedPreferences.getString(WEATHER_DATA, null)
        weatherString?.let {
            val jsonAdapter: JsonAdapter<Weather> = moshiProvider.getMoshi().adapter(Weather::class.java)
            return jsonAdapter.fromJson(weatherString)
        }
        return null
    }

}