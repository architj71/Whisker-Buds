package com.example.whiskerbuds.network
//import com.cloudinary.api.ApiResponse
import com.example.whiskerbuds.model.PetsDataClass
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface PetApiService {

    @GET("/pets")
    suspend fun getPets(): Response<List<PetsDataClass>>

    @POST("/pets")
    suspend fun uploadPetData(@Body petData: PetsDataClass): Response<String>


    @DELETE("/pets/delete/{petId}")
    suspend fun deletePet(@Path("petId") petId: String): Response<ApiResponse>

    @Multipart
    @POST("/pets/donate/{petId}")
    suspend fun donateToPet(
        @Path("petId") petId: String,
        @Part("donationAmount") donationAmount: RequestBody
    ): Response<ApiResponse>
}
