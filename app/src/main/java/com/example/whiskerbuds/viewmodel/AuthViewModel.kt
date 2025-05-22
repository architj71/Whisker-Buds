package com.example.whiskerbuds.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, it.exception?.message)
            }
        }
    }

    fun signUp(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, it.exception?.message)
            }
        }
    }

    fun resetPassword(email: String, callback: (Boolean, String) -> Unit) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Password reset link sent to your email")
            } else {
                callback(false, it.exception?.message ?: "Error")
            }
        }
    }
}
