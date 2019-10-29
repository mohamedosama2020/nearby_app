package com.example.nearby.base.managers

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by mohamed abd elnaby on 16/April/2019
 */

class ManagerSharedPreference {
    private lateinit var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null
    private var context: Context? = null
    private val lat = "lat"
    private val long = "long"
    private val locationProvider = "provider"
    private val appMode = "appMode"// 0 -> Real Time  ,   1 -> Single Time

    fun setLat(latitude: String?) {
        editor?.putString(lat, latitude)
        editor?.commit()
    }

    fun getLat(): String? {
        return sharedPreferences.getString(lat, "")
    }

    fun setMode(mode: Int) {
        editor?.putInt(appMode, mode)
        editor?.commit()
    }

    fun getMode(): Int? {
        return sharedPreferences.getInt(appMode, 0)
    }

    fun setLong(longtiude: String?) {
        editor?.putString(long, longtiude)
        editor?.commit()
    }

    fun getLong(): String? {
        return sharedPreferences.getString(long, "")
    }

    fun setLocationProvider(provider: String?) {
        editor?.putString(locationProvider, provider)
        editor?.commit()
    }

    fun getLocationProvider(): String? {
        return sharedPreferences.getString(locationProvider, "")
    }

    companion object {

        private const val ShareName = "SharedPrefs"
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: ManagerSharedPreference

        @SuppressLint("CommitPrefEdits")
        fun initial(context: Context) {
            instance =
                ManagerSharedPreference()
            instance.context = context
            instance.sharedPreferences =
                context.getSharedPreferences(ShareName, Context.MODE_PRIVATE)
            instance.editor = instance.sharedPreferences.edit()

        }


    }

}
