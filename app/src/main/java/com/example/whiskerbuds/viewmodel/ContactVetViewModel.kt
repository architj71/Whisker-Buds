package com.example.whiskerbuds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.whiskerbuds.model.VetsDataClass
import com.example.whiskerbuds.repository.VetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactVetViewModel(application: Application) : AndroidViewModel(application) {

    private val _vetList = MutableStateFlow<List<VetsDataClass>>(emptyList())
    val vetList: StateFlow<List<VetsDataClass>> = _vetList

    private val vetRepository = VetRepository()

    fun loadVets() {
        viewModelScope.launch {
            _vetList.value = vetRepository.loadVetsFromAssets(getApplication<Application>().applicationContext)
        }
    }
}
