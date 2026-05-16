package com.example.myapplication.biometric

sealed class BiometricAuthState {
    data object Idle : BiometricAuthState()
    data object Authenticating : BiometricAuthState()
    data object Success : BiometricAuthState()
    data class Failed(
        val message: String
    ) : BiometricAuthState()
    data object Unavailable : BiometricAuthState()
}