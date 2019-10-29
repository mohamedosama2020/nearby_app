@file:Suppress("DEPRECATION")

package com.example.nearby.features.nearbyscreen.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nearby.R
import com.example.nearby.base.activities.BaseActivity
import com.example.nearby.base.helper.GpsUtils
import com.example.nearby.base.helper.Utility.locationLiveData
import com.example.nearby.base.managers.ManagerSharedPreference
import com.example.nearby.base.vm.CommonStates
import com.example.nearby.base.vm.LocationStates
import com.example.nearby.entities.nearby.response.Venue
import com.example.nearby.features.nearbyscreen.adapter.VenuesAdapter
import com.example.nearby.features.nearbyscreen.vm.ViewModelNearby
import com.example.nearby.realtime.TrackingService
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_empty_layout.*


@Suppress("DEPRECATION")
class MainActivity : BaseActivity(), GpsUtils.OnGpsListener {


    private lateinit var viewModelNearby: ViewModelNearby
    private val rxPermission = RxPermissions(this)
    private var firstTime = true


    override fun gpsStatus(isGPSEnable: Boolean) {
        if (isGPSEnable)
            checkPermission()
        else {
            setEmptyErrorView(R.drawable.ic_cloud_off_black, getString(R.string.error))
            empty_error_view.visibility = View.VISIBLE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        initObserve()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        val appMode = ManagerSharedPreference.instance.getMode()
        if (appMode == 0) {
            menu?.getItem(0)?.title = getString(R.string.realtime)
        } else {
            menu?.getItem(0)?.title = getString(R.string.singletime)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuRealTime -> {
                if (item.title == getString(R.string.realtime)) {
                    ManagerSharedPreference.instance.setMode(1)
                    item.title = getString(R.string.singletime)
                } else {
                    ManagerSharedPreference.instance.setMode(0)
                    item.title = getString(R.string.realtime)
                }
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            when (resultCode) {
                LocationSettingsStatusCodes.SUCCESS_CACHE -> checkPermission()
                else -> return
            }
        }
    }

    private fun initComponent() {

        //Init ViewModel
        viewModelNearby = ViewModelProviders.of(this).get(ViewModelNearby::class.java)

        //Check GPS Enabled
        GpsUtils(this).turnGPSOn(this)
    }

    /**
     * Initialize Observers for Live Data Variables
     */
    private fun initObserve() {
        viewModelNearby.states.observe(this, Observer {
            if (it != null)
                handleResponse(it)
        })
        locationLiveData.observe(this, Observer {
            if (it != null) {
                hideLoading()
                if (firstTime) {
                    saveToSharedPrefs(it)
                    callData(it.latitude.toString(), it.longitude.toString())
                    firstTime = false
                    //If Mode Is Single Then Stop The Realtime Tracking Service
                    if (ManagerSharedPreference.instance.getMode() == 1)
                        stopService(Intent(this,TrackingService::class.java))
                } else {
                    checkDistance(it)
                }

            }
        })
    }

    /**
     * Checks Distance Between Last Saved Location And Retrieved Location
     * and if the the distance between last saved location and the new location
     * is more than 500 Meters It Calls The API
     */
    private fun checkDistance(location: Location) {

        val savedLat = ManagerSharedPreference.instance.getLat()
        val savedLong = ManagerSharedPreference.instance.getLong()
        val savedProvider = ManagerSharedPreference.instance.getLocationProvider()

        val savedLocation = Location(savedProvider)

        if (!savedLat.isNullOrEmpty() || !savedLong.isNullOrEmpty()) {
            savedLat?.let { savedLocation.latitude = it.toDouble() }
            savedLong?.let { savedLocation.longitude = it.toDouble() }
            savedProvider?.let { savedLocation.provider = it }
        }

        if (location.distanceTo(savedLocation) >= 500f) {
            saveToSharedPrefs(location)
            callData(location.latitude.toString(), location.longitude.toString())
        }

    }

    /**
     * Save Location To Local Storage
     */
    private fun saveToSharedPrefs(location: Location) {
        ManagerSharedPreference.instance.setLat(location.latitude.toString())
        ManagerSharedPreference.instance.setLong(location.longitude.toString())
        ManagerSharedPreference.instance.setLocationProvider(location.provider)
    }

    /**
     * Starts The Tracking Service
     */
    private fun startTrackerService() {
        showLoading()
        startService(Intent(this,TrackingService::class.java))
    }


    private fun handleResponse(response: CommonStates<LocationStates>) {
        when (response) {
            is CommonStates.LoadingShow -> {
                empty_error_view.visibility = View.GONE
                showLoading()
            }
            is CommonStates.NoInternet -> {
                hideLoading()
                setEmptyErrorView(R.drawable.ic_cloud_off_black, getString(R.string.error))
                empty_error_view.visibility = View.VISIBLE

            }
            is CommonStates.Error -> {
                hideLoading()
                setEmptyErrorView(R.drawable.ic_cloud_off_black, getString(R.string.error))
                empty_error_view.visibility = View.VISIBLE
            }
            is CommonStates.Success -> {
                empty_error_view.visibility = View.GONE
                hideLoading()
                handleSuccessResponse(response.data)
            }
        }
    }

    private fun handleSuccessResponse(response: LocationStates) {
        Toast.makeText(this, "Data Retrieved", Toast.LENGTH_SHORT).show()

        when (response) {
            is LocationStates.NearbyPlacesSuccess -> {

                //Check If There No Items
                if (response.data.response.totalResults > 0) {
                    //Set Data To Adapter
                    val venues = mutableListOf<Venue>()
                    response.data.response.groups[0].items.forEach { venues.add(it.venue) }
                    rvVenues.apply {
                        adapter = VenuesAdapter(venues)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        visibility = View.VISIBLE
                    }
                } else {
                    setEmptyErrorView(
                        R.drawable.ic_info_outline_black,
                        getString(R.string.no_data_found)
                    )
                    empty_error_view.visibility = View.VISIBLE
                }

            }

        }
    }

    override fun onStop() {
        super.onStop()
        locationLiveData.value = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelNearby.states.value = null
        stopService(Intent(this,TrackingService::class.java))
        firstTime = false
    }


    private fun callData(lat: String, long: String) {
        rvVenues.visibility = View.GONE
        viewModelNearby.getNearbyPlaces("${lat},${long}")
    }

    @SuppressLint("CheckResult")
    private fun checkPermission() {
        rxPermission.request(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ).subscribe { granted ->
            if (granted) {
                empty_error_view.visibility = View.GONE
                startTrackerService()
            } else {
                Toast.makeText(this, "Permission Revoked", Toast.LENGTH_SHORT).show()
                setEmptyErrorView(R.drawable.ic_cloud_off_black, getString(R.string.error))
                empty_error_view.visibility = View.VISIBLE
            }
        }
    }

    private fun setEmptyErrorView(drawableRes: Int, message: String) {
        ivError.setImageDrawable(ActivityCompat.getDrawable(this, drawableRes))
        tvMessage.text = message
    }
}
