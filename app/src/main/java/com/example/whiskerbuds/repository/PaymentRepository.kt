package com.example.whiskerbuds.repository

import com.example.whiskerbuds.network.PetApiService
import com.example.whiskerbuds.network.RetrofitInstancePet
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class PaymentRepository {

    private val apiService: PetApiService = RetrofitInstancePet.petApiService

    suspend fun donateToShelter(shelterID: String, amount: String): Boolean {
        return try {
            val requestBody = RequestBody.create(MultipartBody.FORM, amount)
            val response = apiService.donateToPet(shelterID, requestBody)
            response.isSuccessful
        } catch (e: IOException) {
            throw Exception("Network error: ${e.message}")
        } catch (e: HttpException) {
            throw Exception("Server error: ${e.message}")
        }
    }
}
