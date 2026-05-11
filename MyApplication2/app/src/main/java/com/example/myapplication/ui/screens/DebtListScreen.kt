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

@Composable
fun DebtListScreen(
    navController: NavController,
    viewModel: DebtViewModel
) {

    val debts by viewModel.debts.collectAsState()

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
        ) {

            Text(
                text = "Трекер боргів",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                items(debts) { debt ->

                    DebtItem(
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
fun DebtItem(
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
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Залишилось: ${debt.currentAmount} грн"
            )

            Text(
                text = "Початково: ${debt.initialAmount} грн"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    if (debt.isReturned)
                        "Статус: Погашено"
                    else
                        "Статус: Активний"
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Sync: ${debt.syncStatus}"
            )
        }
    }
}