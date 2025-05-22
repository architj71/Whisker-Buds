package com.example.whiskerbuds.network

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("msg")
    val msg: String? = null
)
