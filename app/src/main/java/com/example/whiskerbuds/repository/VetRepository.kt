package com.example.whiskerbuds.repository

import android.content.Context
import com.example.whiskerbuds.model.VetsDataClass
import com.google.gson.Gson

class VetRepository {
    fun loadVetsFromAssets(context: Context): List<VetsDataClass> {
        return try {
            val inputStream = context.assets.open("vet.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, Charsets.UTF_8)
            Gson().fromJson(json, Array<VetsDataClass>::class.java).toList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
