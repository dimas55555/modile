package com.example.myapplication

import com.example.myapplication.biometric.BiometricAuthState
import org.junit.Assert.assertEquals
import org.junit.Test

class BiometricStateTest {

    @Test
    fun biometricState_shouldStartIdle() {

        val state =
            BiometricAuthState.Idle

        assertEquals(
            BiometricAuthState.Idle,
            state
        )
    }

    @Test
    fun biometricFailed_shouldContainMessage() {

        val state =
            BiometricAuthState.Failed(
                "Sensor unavailable"
            )

        assertEquals(
            "Sensor unavailable",
            state.message
        )
    }
}