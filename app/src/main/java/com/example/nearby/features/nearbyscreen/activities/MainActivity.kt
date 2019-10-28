package com.example.nearby.features.nearbyscreen.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.nearby.R
import com.example.nearby.base.activities.BaseActivity
import com.example.nearby.base.vm.CommonStates
import com.example.nearby.base.vm.LocationStates
import com.example.nearby.features.nearbyscreen.vm.ViewModelNearby

@Suppress("DEPRECATION")
class MainActivity : BaseActivity() {


    private lateinit var viewModelNearby: ViewModelNearby


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
        initObserve()
        viewModelNearby.getNearbyPlaces("40.74224,-73.99386")
    }

    private fun initComponent() {
        viewModelNearby = ViewModelProviders.of(this).get(ViewModelNearby::class.java)

    }

    private fun initObserve() {
        viewModelNearby.states.observe(this, Observer {
            if (it != null)
                handleResponse(it)
        })
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
}
