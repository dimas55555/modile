package com.example.myapplication.biometric

import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.StateFlow

interface BiometricController {

    val authState: StateFlow<BiometricAuthState>

    fun checkAvailability(): BiometricAvailability

    fun authenticate(
        activity: FragmentActivity,
        reason: String
    )
}