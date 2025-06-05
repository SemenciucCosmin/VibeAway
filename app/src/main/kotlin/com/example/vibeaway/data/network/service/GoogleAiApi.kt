package com.example.vibeaway.data.network.service

import com.example.vibeaway.data.network.model.GoogleAiRequestDTO
import com.example.vibeaway.data.network.model.GoogleAiResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GoogleAiApi {
    @POST("v1/models/gemini-1.5-flash:generateContent")
    suspend fun generateContent(
        @Body request: GoogleAiRequestDTO,
        @Query("key") apiKey: String
    ): Response<GoogleAiResponseDTO>
}
