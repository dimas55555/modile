package com.example.myapplication.biometric

import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeBiometricManager :
    BiometricController {

    private val _authState =
        MutableStateFlow<BiometricAuthState>(
            BiometricAuthState.Idle
        )

    override val authState:
            StateFlow<BiometricAuthState>
        get() = _authState

    override fun checkAvailability():
            BiometricAvailability {

        return BiometricAvailability.FINGERPRINT
    }

    override fun authenticate(
        activity: FragmentActivity,
        reason: String
    ) {

        _authState.value =
            BiometricAuthState.Authenticating

        _authState.value =
            BiometricAuthState.Success
    }
}