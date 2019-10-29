package com.example.nearby.usecases.nearby

import androidx.lifecycle.MutableLiveData
import com.example.nearby.base.usecase.MainModel
import com.example.nearby.base.vm.CommonStates
import com.example.nearby.base.vm.LocationStates
import com.example.nearby.entities.nearby.response.NearbyPlacesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ModelNearby : MainModel() {


    fun getNearbyPlaces(
        states: MutableLiveData<CommonStates<LocationStates>>,
        location: String
    ) {
        states.value = CommonStates.LoadingShow
        getDisposable(states, true)?.add(
            apiClient.getNearbyPlaces(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NearbyPlacesResponse>() {
                    override fun onSuccess(t: NearbyPlacesResponse) {
                        states.value = CommonStates.Success(LocationStates.NearbyPlacesSuccess(t))
                    }

                    override fun onError(e: Throwable) {
                        states.value = CommonStates.Error(e)
                    }
                })
        )
    }


}