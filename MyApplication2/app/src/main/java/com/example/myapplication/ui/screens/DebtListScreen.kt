package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val allDebts by viewModel.debts.collectAsState()
    val socketState by viewModel.socketState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var expandedFilters by remember { mutableStateOf(false) }
    var sortBy by remember { mutableStateOf("date") }

    val debts = remember(allDebts, searchQuery, sortBy) {

        var filtered = allDebts

        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
        }

        filtered = when (sortBy) {
            "amount" -> filtered.sortedByDescending { it.currentAmount }
            "active" -> filtered.filter { !it.isReturned }
            else -> filtered.sortedByDescending { it.id }
        }

        filtered
    }

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
        },

        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        onClick = {
                            if (socketState == SocketState.Connected) {
                                viewModel.disconnectSocket()
                            } else {
                                viewModel.connectSocket()
                            }
                        }
                    ) {
                        Text(
                            if (socketState == SocketState.Connected)
                                "Disconnect"
                            else
                                "Connect"
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate(NavRoutes.PROFILE)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Профіль"
                        )
                    }
                }
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
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = when (socketState) {
                        SocketState.Connected ->
                            Icons.Default.Circle
                        SocketState.Connecting ->
                            Icons.Default.Circle
                        SocketState.Reconnecting ->
                            Icons.Default.Circle
                        SocketState.Disconnected ->
                            Icons.Default.Circle
                    },
                    contentDescription = null,
                    tint = socketColor(socketState)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = when (socketState) {
                        SocketState.Connected -> "Connected"
                        SocketState.Connecting -> "Connecting"
                        SocketState.Reconnecting -> "Reconnecting"
                        SocketState.Disconnected -> "Disconnected"
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    expandedFilters = !expandedFilters
                }
            ) {

                Column(modifier = Modifier.padding(12.dp)) {

                    Text(
                        text = if (expandedFilters)
                            "🔽 Фільтри"
                        else
                            "🔍 Пошук та фільтри"
                    )

                    if (expandedFilters) {

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Пошук") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row {

                            FilterChip(
                                selected = sortBy == "date",
                                onClick = { sortBy = "date" },
                                label = { Text("Дата") }
                            )

                            Spacer(Modifier.width(8.dp))

                            FilterChip(
                                selected = sortBy == "amount",
                                onClick = { sortBy = "amount" },
                                label = { Text("Сума") }
                            )

                            Spacer(Modifier.width(8.dp))

                            FilterChip(
                                selected = sortBy == "active",
                                onClick = { sortBy = "active" },
                                label = { Text("Активні") }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.clearReturnedDebts()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Очистити погашені борги")
            }

            Spacer(modifier = Modifier.height(16.dp))
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

fun socketColor(
    state: SocketState
): Color {

    return when (state) {

        SocketState.Connected ->
            Color(0xFF4CAF50)

        SocketState.Connecting ->
            Color(0xFFFFC107)

        SocketState.Reconnecting ->
            Color(0xFFFF9800)

        SocketState.Disconnected ->
            Color(0xFFF44336)
    }
}