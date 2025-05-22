package com.example.whiskerbuds.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whiskerbuds.repository.PaymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {
    private val repository = PaymentRepository()
    private val _paymentStatus = MutableStateFlow<String?>(null)
    val paymentStatus: StateFlow<String?> = _paymentStatus

    fun processDonation(shelterID: String, amount: String) {
        viewModelScope.launch {
            try {
                val success = repository.donateToShelter(shelterID, amount)
                _paymentStatus.value = if (success) "Donation Successful" else "Donation Failed"
            } catch (e: Exception) {
                _paymentStatus.value = "Error: ${e.message}"
            }
        }
    }
}
