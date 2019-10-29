package com.example.nearby.features.nearbyscreen.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.nearby.R
import com.example.nearby.base.activities.BaseActivity
import com.example.nearby.base.helper.GpsUtils
import com.example.nearby.base.helper.Utility.locationLiveData
import com.example.nearby.base.managers.ManagerSharedPreference
import com.example.nearby.base.vm.CommonStates
import com.example.nearby.base.vm.LocationStates
import com.example.nearby.features.nearbyscreen.TrackingService
import com.example.nearby.features.nearbyscreen.vm.ViewModelNearby
import com.tbruyelle.rxpermissions2.RxPermissions


@Suppress("DEPRECATION")
class MainActivity : BaseActivity(),GpsUtils.OnGpsListener {

    private lateinit var viewModelNearby: ViewModelNearby
    private val rxPermission = RxPermissions(this)

    @SuppressLint("CheckResult")
    override fun gpsStatus(isGPSEnable: Boolean) {
        if(isGPSEnable){
            //Check Permission
            rxPermission.request(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ).subscribe{granted ->
                if (granted){
                    startTrackerService()
                    showLoading()
                }
                else
                    Toast.makeText(this, "Permission Revoked", Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        initObserve()
    }

    private fun initComponent() {


        //Init ViewModel
        viewModelNearby = ViewModelProviders.of(this).get(ViewModelNearby::class.java)

        //Check GPS Enabled
        GpsUtils(this).turnGPSOn(this)
    }

    private fun initObserve() {
        viewModelNearby.states.observe(this, Observer {
            if (it != null)
                handleResponse(it)
        })
        locationLiveData.observe(this, Observer {
            if (it != null){
                checkDistance(it)
            }
        })
    }

    private fun checkDistance(location: Location) {
        val lat = ManagerSharedPreference.instance.getLat()
        val long = ManagerSharedPreference.instance.getLong()
        val provider = ManagerSharedPreference.instance.getLocationProvider()

        val savedLocation = Location(provider)

        if(!lat.isNullOrEmpty() || !long.isNullOrEmpty()){
            lat?.let { savedLocation.latitude = it.toDouble() }
            long?.let { savedLocation.longitude = it.toDouble() }
            provider?.let { savedLocation.provider = it }
        }

        if(lat.isNullOrEmpty() || long.isNullOrEmpty()){
            callData(location.latitude.toString(),location.longitude.toString())
            saveToSharedPrefs(location)
        }else if(location.distanceTo(savedLocation) >= 500f){
            saveToSharedPrefs(location)
            callData(location.latitude.toString(),location.longitude.toString())
        }else{
            saveToSharedPrefs(location)
        }

    }

    private fun saveToSharedPrefs(location: Location) {
        ManagerSharedPreference.instance.setLat(location.latitude.toString())
        ManagerSharedPreference.instance.setLong(location.longitude.toString())
    }

    private fun startTrackerService() {
        showLoading()
        //Start The Service
        startService(Intent(this, TrackingService::class.java))
    }

    private fun handleResponse(response: CommonStates<LocationStates>) {
        when (response) {
            is CommonStates.LoadingShow -> {
                showLoading()
            }
            is CommonStates.NoInternet -> {
                hideLoading()
                //Handle No Internet Here
            }
            is CommonStates.Error -> {
                hideLoading()
                //Handle Error Here
            }
            is CommonStates.Success -> {
                hideLoading()
                handleSuccessResponse(response.data)
            }
        }
    }

    private fun handleSuccessResponse(response: LocationStates) {

    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, TrackingService::class.java))
    }

    private fun callData(lat:String,long:String){
        viewModelNearby.getNearbyPlaces("${lat},${long}")

    }
}
