package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.biometric.BiometricAuthState
import com.example.myapplication.viewmodel.SecurityViewModel

@Composable
fun LockScreen(
    activity: FragmentActivity,
    viewModel: SecurityViewModel,
    onSuccess: () -> Unit
) {

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is BiometricAuthState.Success) {
            onSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Розблокування застосунку",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.authenticate(activity, "Розблокувати додаток")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Підтвердити біометрією")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when (authState) {

                BiometricAuthState.Idle -> "Очікування"
                BiometricAuthState.Authenticating -> "Перевірка..."
                BiometricAuthState.Success -> "Успішно"
                is BiometricAuthState.Failed -> (authState as BiometricAuthState.Failed).message
                BiometricAuthState.Unavailable -> "Недоступно"
            },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}