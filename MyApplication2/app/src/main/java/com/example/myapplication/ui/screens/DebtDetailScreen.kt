package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.viewmodel.DebtViewModel

@Composable
fun DebtDetailScreen(
    navController: NavController,
    viewModel: DebtViewModel,
    debtId: Int
) {

    val debts by viewModel.debts.collectAsState()

    val debt = debts.find {
        it.id == debtId
    }

    debt ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Деталі боргу",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        DebtInfo(debt)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                navController.navigate(
                    "returnDebt/${debt.id}"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Повернути частину")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {

                viewModel.returnDebt(
                    debt = debt,
                    amount = debt.currentAmount
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Закрити повністю")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {

                viewModel.deleteDebt(debt)

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Видалити")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Назад")
        }
    }
}

@Composable
fun DebtInfo(
    debt: DebtEntity
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = debt.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text =
                    "Початкова сума: ${debt.initialAmount}"
            )

            Text(
                text =
                    "Поточний борг: ${debt.currentAmount}"
            )

            Text(
                text =
                    if (debt.isReturned)
                        "Борг погашено"
                    else
                        "Борг активний"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sync: ${debt.syncStatus}"
            )
        }
    }
}