package com.example.myapplication.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppBiometricManager(
    private val context: Context
) : BiometricController {

    private val _authState =
        MutableStateFlow<BiometricAuthState>(
            BiometricAuthState.Idle
        )

    override val authState:
            StateFlow<BiometricAuthState>
        get() = _authState

    override fun checkAvailability():
            BiometricAvailability {

        val biometricManager =
            BiometricManager.from(context)

        return when (
            biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            )
        ) {

            BiometricManager.BIOMETRIC_SUCCESS ->
                BiometricAvailability.FINGERPRINT

            else ->
                BiometricAvailability.NONE
        }
    }

    override fun authenticate(
        activity: FragmentActivity,
        reason: String
    ) {

        _authState.value =
            BiometricAuthState.Authenticating

        val executor =
            ContextCompat.getMainExecutor(context)

        val biometricPrompt =
            BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {

                        _authState.value =
                            BiometricAuthState.Success
                    }

                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {

                        _authState.value =
                            BiometricAuthState.Failed(
                                errString.toString()
                            )
                    }

                    override fun onAuthenticationFailed() {

                        _authState.value =
                            BiometricAuthState.Failed(
                                "Authentication failed"
                            )
                    }
                }
            )

        val promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric authentication")
                .setSubtitle(reason)
                .setNegativeButtonText("Cancel")
                .build()

        biometricPrompt.authenticate(promptInfo)
    }
}