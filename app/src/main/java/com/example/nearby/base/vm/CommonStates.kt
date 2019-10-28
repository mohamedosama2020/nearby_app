package com.example.nearby.base.vm

import com.example.nearby.entities.nearby.response.NearbyPlacesResponse


sealed class CommonStates<out T> {
    object LoadingShow : CommonStates<Nothing>()
    object RefreshView : CommonStates<Nothing>()
    object NoInternet : CommonStates<Nothing>()
    object LoadingShowMore : CommonStates<Nothing>()
    data class EmptyState(val text: String, val Image: Int) : CommonStates<Nothing>()
    data class ShowMSG(val text: String) : CommonStates<Nothing>()
    data class Success<out R>(val data: R) : CommonStates<R>()
    data class Error(val exp: Throwable) : CommonStates<Nothing>()
}

sealed class LocationStates {
    data class NearbyPlacesSuccess(val data: NearbyPlacesResponse) : LocationStates()
}


