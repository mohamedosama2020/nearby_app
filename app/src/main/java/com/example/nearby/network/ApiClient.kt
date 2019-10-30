package com.example.nearby.network


import VenuePhotoResponse
import com.example.nearby.entities.nearbyvenues.response.NearbyPlacesResponse
import io.reactivex.Single


class ApiClient private constructor() : BaseClientApi() {
    var endPoint: EndPoint = retofitClient.create(EndPoint::class.java)

    companion object {
        lateinit var apiClient: ApiClient
        fun initApiClient() {
            apiClient = ApiClient()
        }
    }


    fun getNearbyPlaces(location: String): Single<NearbyPlacesResponse> {
        return endPoint.getNearbyPlaces(location = location)
    }

    fun getPlacePhoto(venueID: String): Single<VenuePhotoResponse> {
        return endPoint.getPlacePhoto(venueID)
    }

}
