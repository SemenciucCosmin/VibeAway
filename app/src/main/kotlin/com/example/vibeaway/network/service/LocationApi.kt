package com.example.vibeaway.network.service

import com.example.vibeaway.network.model.GenericAmadeusDTO
import com.example.vibeaway.network.model.LocationActivityDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api service for Amadeus location related calls
 * See https://developers.amadeus.com
 */
interface LocationApi {
    /**
     * Retrieves a list of activities in a specified [radius] for a certain location
     * determined by [latitude] and [longitude]
     */
    @GET("/v1/shopping/activities")
    suspend fun getActivities(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int,
    ): Response<GenericAmadeusDTO<List<LocationActivityDTO>>>
}
