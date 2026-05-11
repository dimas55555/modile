package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.DebtViewModel

@Composable
fun ProfileScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Профіль",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Користувач: Дмитро"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "Стратегія: Offline-first"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "Локальне сховище: Room Database"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "API: Fake REST API"
                )
            }
        }
    }
}