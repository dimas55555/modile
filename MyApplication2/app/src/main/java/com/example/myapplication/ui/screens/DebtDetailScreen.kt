package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.DebtViewModel

@Composable
fun DebtDetailScreen(
    navController: NavController,
    viewModel: DebtViewModel,
    debtId: Int
) {

    val debt = viewModel.getDebtById(debtId)
    val person = debt?.let {
        viewModel.getPersonById(it.personId)
    }

    debt ?: return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = person?.name ?: "",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Борг: ${debt.title}")
        Text("Початкова сума: ${debt.initialAmount}")
        Text("Залишок: ${debt.currentAmount}")
        Text("Телефон: ${person?.phone}")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate(
                    "returnDebt/${debt.id}"
                )
            }
        ) {
            Text("Повернути частину")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                viewModel.returnDebt(
                    debt.id,
                    debt.currentAmount
                )
            }
        ) {
            Text("Закрити повністю")
        }
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Назад")
        }
    }
}