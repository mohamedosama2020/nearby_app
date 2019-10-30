package com.example.nearby.base.vm

import VenuePhotoResponse
import com.example.nearby.entities.nearbyvenues.response.NearbyPlacesResponse


sealed class CommonStates<out T> {
    object LoadingShow : CommonStates<Nothing>()
    object RefreshView : CommonStates<Nothing>()
    object NoInternet : CommonStates<Nothing>()
    object LoadingShowMore : CommonStates<Nothing>()
    data class EmptyState(val text: String, val Image: Int) : CommonStates<Nothing>()
    data class PhotoError(val exp: Throwable) : CommonStates<Nothing>()
    data class Success<out R>(val data: R) : CommonStates<R>()
    data class Error(val exp: Throwable) : CommonStates<Nothing>()
}

sealed class LocationStates {
    data class NearbyPlacesSuccess(val data: NearbyPlacesResponse) : LocationStates()
    data class NearbyPhotoSuccess(val data: VenuePhotoResponse) : LocationStates()
}


