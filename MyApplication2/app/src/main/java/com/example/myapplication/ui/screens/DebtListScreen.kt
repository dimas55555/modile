package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.navigation.NavRoutes
import com.example.myapplication.viewmodel.DebtViewModel
import com.example.myapplication.websocket.SocketState

@Composable
fun DebtListScreen(
    navController: NavController,
    viewModel: DebtViewModel
) {

    val debts by viewModel.debts.collectAsState()

    val socketState by
    viewModel.socketState.collectAsState()

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = {

                    navController.navigate(
                        NavRoutes.ADD_PERSON_WITH_DEBT
                    )
                }
            ) {

                Text("+")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Трекер боргів",
                style =
                    MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "WebSocket: $socketState"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row {

                Button(
                    onClick = {

                        viewModel.connectSocket()
                    }
                ) {

                    Text("Connect")
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                OutlinedButton(
                    onClick = {

                        viewModel.disconnectSocket()
                    }
                ) {

                    Text("Disconnect")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {

                items(debts) { debt ->

                    DebtCard(
                        debt = debt,
                        onClick = {

                            navController.navigate(
                                "${NavRoutes.DETAIL}/${debt.id}"
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DebtCard(
    debt: DebtEntity,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable {

                onClick()
            }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = debt.title,
                style =
                    MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    "Поточний борг: ${debt.currentAmount}"
            )

            Text(
                text =
                    "Початковий борг: ${debt.initialAmount}"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    if (debt.isReturned)
                        "Погашено"
                    else
                        "Активний"
            )

            Text(
                text =
                    "Sync: ${debt.syncStatus}"
            )
        }
    }
}