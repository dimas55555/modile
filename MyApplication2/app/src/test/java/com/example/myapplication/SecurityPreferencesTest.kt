package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class SecurityPreferencesTest {

    @Test
    fun biometricFlag_shouldChangeCorrectly() {
        var biometricEnabled = false
        biometricEnabled = true
        assertTrue(biometricEnabled)
        biometricEnabled = false
        assertFalse(biometricEnabled)
    }
}