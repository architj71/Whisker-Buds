package com.example.whiskerbuds.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.whiskerbuds.repository.PetRepository
import com.example.whiskerbuds.model.PetsDataClass
import com.example.whiskerbuds.network.RetrofitInstancePet.petApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetViewModel(
    application: Application,
    private val repository: PetRepository
) : AndroidViewModel(application) {

    // StateFlow for holding pet data
    private val _petList = MutableStateFlow<List<PetsDataClass>?>(null)
    val petList: StateFlow<List<PetsDataClass>?> = _petList

    private val _dogList = MutableStateFlow<List<PetsDataClass>?>(null)
    val dogList: StateFlow<List<PetsDataClass>?> = _dogList

    private val _catList = MutableStateFlow<List<PetsDataClass>?>(null)
    val catList: StateFlow<List<PetsDataClass>?> = _catList

    private val _cowList = MutableStateFlow<List<PetsDataClass>?>(null)
    val cowList: StateFlow<List<PetsDataClass>?> = _cowList

    private val _birdList = MutableStateFlow<List<PetsDataClass>?>(null)
    val birdList: StateFlow<List<PetsDataClass>?> = _birdList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchPets() // Fetch data when ViewModel is created
    }

    private fun fetchPets() {
        viewModelScope.launch {
            try {
                val pets = repository.getPets()
                val filteredPets = pets.filter { it.petAdoptionTime < System.currentTimeMillis() }

                // Update StateFlow with the fetched data
                _petList.value = filteredPets

                // Filter and update lists for specific pet types
                _dogList.value = filteredPets.filter { it.petType == "Dog" }
                _catList.value = filteredPets.filter { it.petType == "Cat" }
                _cowList.value = filteredPets.filter { it.petType == "Cow" }
                _birdList.value = filteredPets.filter { it.petType == "Bird" }

            } catch (e: Exception) {
                // Handle exceptions and update error state
                _errorMessage.value = "Error fetching pets: ${e.message}"
                Log.e("PetViewModel", "Error fetching pets: ${e.message}")
            }
        }
    }

    fun uploadPet(petData: PetsDataClass, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val message = repository.uploadPetData(petData)
                onResult(true, message)
            } catch (e: Exception) {
                onResult(false, "Error: ${e.message}")
            }
        }
    }



    fun deletePet(petId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = petApiService.deletePet(petId)
                if (response.isSuccessful) {
                    Log.d("PetViewModel", "Pet Adopted Successfully")
                } else {
                    Log.e("PetViewModel", "Failed to delete pet: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("PetViewModel", "Error deleting pet", e)
            }
        }
    }
}