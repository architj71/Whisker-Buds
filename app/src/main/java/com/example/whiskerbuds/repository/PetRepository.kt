package com.example.whiskerbuds.repository

import com.example.whiskerbuds.model.PetsDataClass
import com.example.whiskerbuds.network.ApiResponse
import com.example.whiskerbuds.network.PetApiService
import com.example.whiskerbuds.network.RetrofitInstancePet
import retrofit2.HttpException
import java.io.IOException


class PetRepository {

    private val apiService: PetApiService = RetrofitInstancePet.petApiService

    suspend fun getPets(): List<PetsDataClass> {
        return try {
            val response = apiService.getPets()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw Exception("Network error: ${e.message}")
        }
    }

    suspend fun uploadPetData(petData: PetsDataClass): String {
        return try {
            val response = apiService.uploadPetData(petData)
            if (response.isSuccessful) {
                response.body() ?: "Pet added successfully"
            } else {
                "Error: ${response.errorBody()?.string()}"
            }
        } catch (e: Exception) {
            "Failed: ${e.message}"
        }
    }


}
