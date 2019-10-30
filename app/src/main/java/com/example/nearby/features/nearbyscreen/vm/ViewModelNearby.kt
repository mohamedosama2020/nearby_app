package com.example.nearby.features.nearbyscreen.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nearby.base.vm.CommonStates
import com.example.nearby.base.vm.LocationStates
import com.example.nearby.usecases.nearby.ModelNearby


class ViewModelNearby : ViewModel() {

    var states = MutableLiveData<CommonStates<LocationStates>>()
    private var model: ModelNearby = ModelNearby()

    fun getNearbyPlaces(location: String ) {
        model.getNearbyPlaces(states, location)
    }

    fun getPlacePhoto(venueID: String ) {
        model.getPlacePhoto(states, venueID)
    }

    override fun onCleared() {
        super.onCleared()
        model.clear()
    }
}