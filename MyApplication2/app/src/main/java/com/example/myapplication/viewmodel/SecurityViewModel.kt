package com.example.myapplication.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.myapplication.biometric.*
import com.example.myapplication.data.preferences.SecurityPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SecurityViewModel(
    private val biometricManager:
    BiometricController,
    private val preferences:
    SecurityPreferences
) : ViewModel() {

    val authState =
        biometricManager.authState

    private val _enabled =
        MutableStateFlow(
            preferences.isBiometricEnabled()
        )

    val biometricEnabled:
            StateFlow<Boolean>
        get() = _enabled

    fun toggleBiometric(
        enabled: Boolean
    ) {

        preferences.setBiometricEnabled(enabled)

        _enabled.value = enabled
    }

    fun getAvailability():
            BiometricAvailability {

        return biometricManager
            .checkAvailability()
    }

    fun authenticate(
        activity: FragmentActivity,
        reason: String
    ) {

        biometricManager.authenticate(
            activity,
            reason
        )
    }
}