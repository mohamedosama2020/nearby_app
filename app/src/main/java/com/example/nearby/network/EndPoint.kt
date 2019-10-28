package com.example.nearby.network

import com.example.nearby.BuildConfig
import com.example.nearby.entities.nearby.response.NearbyPlacesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface EndPoint {


    @GET("venues/explore")
    fun getNearbyPlaces(
        @Query("ll") location: String,
        @Query("radius") radius: String = "1000",
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("v") date: String = "20191029"
    ): Single<NearbyPlacesResponse>


}