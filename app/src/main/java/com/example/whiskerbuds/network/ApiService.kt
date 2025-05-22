package com.example.whiskerbuds.network

import com.example.whiskerbuds.model.ShelterDataClass
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/shelter")
    suspend fun uploadData(@Body shelterData: ShelterDataClass): Response<Unit>  // Updated from ResponseBody

    @GET("/shelter") // Added leading `/` for consistency
    suspend fun getShelters(): List<ShelterDataClass>

    // Donation API
    @POST("/shelter/{id}/donate")
    suspend fun donateToShelter(
        @Path("id") shelterId: String,
        @Body amount: RequestBody
    ): Response<Unit>  // Updated from ResponseBody
}
