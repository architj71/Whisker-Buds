package com.example.whiskerbuds.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.whiskerbuds.repository.PetRepository

class PetViewModelFactory(
    private val repository: PetRepository,
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PetViewModel::class.java)) {
            PetViewModel(application, repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
