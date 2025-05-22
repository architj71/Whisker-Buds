package com.example.whiskerbuds.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstancePet {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://masterstack-sk23.onrender.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val petApiService: PetApiService by lazy {
        retrofit.create(PetApiService::class.java)
    }
}
