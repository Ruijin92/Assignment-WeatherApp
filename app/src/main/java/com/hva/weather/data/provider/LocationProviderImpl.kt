package com.hva.weather.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.hva.weather.data.db.XU.apixu.entity.WeatherLocation
import com.hva.weather.internal.LocationPermissionNotGrantedException
import com.hva.weather.internal.asDeferred
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

/**
 * Handel all the location stuff but if the fusedlocationprovider cant find anything it will return null
 */
class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : ILocationProvider, PreferenceProvider(context) {

    private val contextApp = context.applicationContext

    /**
     * Building the String which is needed for the api call either a new location name or the cords
     */
    override suspend fun getPreferredLocationString(): String {
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocation().await() ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException) {
                return "${getCustomLocationName()}"
            }
        } else {
            return "${getCustomLocationName()}"
        }
    }

    /**
     * Checks if the device location has changed in the database or in the custom location field in settings
     */
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }
        return deviceLocationChanged || hasCustomeLoactionChanged(lastWeatherLocation)
    }

    /**
     * Checks if the custom location is different then the location which is in the database
     */
    private fun hasCustomeLoactionChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    /**
     * Gets the String of the custom location which is set in the settings
     */
    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }


    /**
     * Checks if the location is changed in the database if not it returns false otherwise true
     */
    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation()) {
            return false
        }
        val deviceLocation = getLastDeviceLocation().await() ?: return false

        //Checks if the location is near the older one
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold

    }

    /**
     * Gets the location from the device, but the location library just return Tasks, so deferred is used to cast the
     * task to a deferred object
     */
    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        if (hasLoactionPremission()) {
            return fusedLocationProviderClient.lastLocation.asDeferred()
        } else {
            throw LocationPermissionNotGrantedException()
        }
    }

    /**
     * Checks if the user is using the device location or a location he set himself
     */
    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }


    /**
     * Checks if the user has granted the Permission to use the location
     */
    private fun hasLoactionPremission(): Boolean {
        return ContextCompat.checkSelfPermission(
            contextApp,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}