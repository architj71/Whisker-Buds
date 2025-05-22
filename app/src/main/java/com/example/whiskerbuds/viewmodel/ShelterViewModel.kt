package com.example.whiskerbuds.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whiskerbuds.model.ShelterDataClass
import com.example.whiskerbuds.repository.ShelterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

class ShelterViewModel(private val repository: ShelterRepository) : ViewModel() {

    private val _registrationStatus = MutableStateFlow<String?>(null)
    val registrationStatus: StateFlow<String?> get() = _registrationStatus

    private val _shelterList = MutableStateFlow<List<ShelterDataClass>>(emptyList())
    val shelterList: StateFlow<List<ShelterDataClass>> get() = _shelterList

    private val _donationStatus = MutableStateFlow<String?>(null)
    val donationStatus: StateFlow<String?> get() = _donationStatus

    fun registerShelter(shelter: ShelterDataClass) {
        viewModelScope.launch {
            try {
                val response: Response<Unit> = repository.registerShelter(shelter)
                if (response.isSuccessful) {
                    _registrationStatus.value = "Shelter Registered Successfully"
                } else {
                    _registrationStatus.value = "Error: ${response.errorBody()?.string()}"
                }
            } catch (e: IOException) {
                _registrationStatus.value = "Network Error: ${e.message}"
            } catch (e: Exception) {
                _registrationStatus.value = "Unexpected Error: ${e.message}"
            }
        }
    }

    fun getShelters() {
        viewModelScope.launch {
            try {
                val shelters = repository.getAllShelters()
                _shelterList.value = shelters
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun donateToShelter(shelterId: String, amount: String) {
        viewModelScope.launch {
            try {
                val success = repository.donateToShelter(shelterId, amount)
                _donationStatus.value = if (success) "Donation Successful!" else "Donation Failed."
            } catch (e: Exception) {
                _donationStatus.value = "Error: ${e.message}"
            }
        }
    }
}
