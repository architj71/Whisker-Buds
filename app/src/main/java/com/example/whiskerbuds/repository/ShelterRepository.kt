package com.example.whiskerbuds.repository

import com.example.whiskerbuds.model.ShelterDataClass
import com.example.whiskerbuds.network.ApiService
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ShelterRepository(private val api: ApiService) {  // Accept ApiService instance

    suspend fun registerShelter(shelter: ShelterDataClass): Response<Unit> {
        return api.uploadData(shelter)  // Return the actual Response object
    }


    suspend fun getAllShelters(): List<ShelterDataClass> {
        return try {
            api.getShelters()
        } catch (e: Exception) {
            emptyList()  // Return an empty list in case of an error
        }
    }

    suspend fun donateToShelter(shelterID: String, amount: String): Boolean {
        return try {
            val requestBody = RequestBody.create(okhttp3.MultipartBody.FORM, amount)
            val response = api.donateToShelter(shelterID, requestBody)
            response.isSuccessful
        } catch (e: IOException) {
            throw Exception("Network error: ${e.message}")
        } catch (e: HttpException) {
            throw Exception("Server error: ${e.message}")
        }
    }
}
