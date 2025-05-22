package com.example.whiskerbuds.model
import com.google.gson.annotations.SerializedName

data class VetsDataClass(
    @SerializedName("VetName")
    val vetName: String,

    @SerializedName("contactNo")
    val contactNo: String,

    @SerializedName("contactEmail")
    val contactEmail: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("imgUrl")
    val imgUrl: String? = null
)
