package com.example.vibeaway.data.network.service

import com.example.vibeaway.data.network.model.ActivityDetailsDTO
import com.example.vibeaway.data.network.model.GenericAmadeusDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api service for Amadeus location related calls
 * See https://developers.amadeus.com
 */
interface ActivitiesDetailsApi {
    /**
     * Retrieves a list of activities in a specified [radius] for a certain location
     * determined by [latitude] and [longitude]
     */
    @GET("/v1/shopping/activities")
    suspend fun getActivitiesDetails(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int,
    ): Response<GenericAmadeusDTO<List<ActivityDetailsDTO>>>
}
