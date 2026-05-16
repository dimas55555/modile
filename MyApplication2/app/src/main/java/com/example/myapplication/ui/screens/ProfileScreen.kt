package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.navigation.NavRoutes

@Composable
fun ProfileScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
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

                Text("Користувач: Дмитро")
                Spacer(Modifier.height(8.dp))

                Text("Стратегія: Offline-first")
                Spacer(Modifier.height(8.dp))

                Text("Локальне сховище: Room Database")
                Spacer(Modifier.height(8.dp))

                Text("API: Fake REST API")

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = {
                            navController.navigate(NavRoutes.LIST)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("На головний екран")
                    }

                    OutlinedButton(
                        onClick = {
                            navController.navigate(NavRoutes.SECURITY)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Налаштування безпеки")
                    }
                }
            }
        }
    }
}