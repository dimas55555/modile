package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.biometric.BiometricAvailability
import com.example.myapplication.viewmodel.SecurityViewModel

@Composable
fun SecuritySettingsScreen(
    viewModel: SecurityViewModel
) {

    val enabled by viewModel.biometricEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Налаштування безпеки",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Тип біометрії: ${
                when (viewModel.getAvailability()) {
                    BiometricAvailability.FINGERPRINT -> "Відбиток пальця"
                    BiometricAvailability.FACE_ID -> "Розпізнавання обличчя"
                    BiometricAvailability.NONE -> "Недоступно"
                }
            }"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Увімкнути біометрію")

            Switch(
                checked = enabled,
                onCheckedChange = {
                    viewModel.toggleBiometric(it)
                }
            )
        }
    }
}