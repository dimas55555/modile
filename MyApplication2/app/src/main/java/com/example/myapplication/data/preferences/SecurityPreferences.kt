package com.example.myapplication.data.preferences

import android.content.Context

class SecurityPreferences(
    context: Context
) {

    private val prefs =
        context.getSharedPreferences(
            "security_prefs",
            Context.MODE_PRIVATE
        )

    fun setBiometricEnabled(
        enabled: Boolean
    ) {

        prefs.edit()
            .putBoolean(
                "biometric_enabled",
                enabled
            )
            .apply()
    }

    fun isBiometricEnabled(): Boolean {

        return prefs.getBoolean(
            "biometric_enabled",
            false
        )
    }
}