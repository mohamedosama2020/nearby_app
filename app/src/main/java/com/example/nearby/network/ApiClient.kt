package com.example.nearby.network


import com.example.nearby.entities.nearby.response.NearbyPlacesResponse
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

}
