package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.DebtViewModel

@Composable
fun ReturnDebtScreen(
    navController: NavController,
    viewModel: DebtViewModel,
    debtId: Int
) {

    val debts by viewModel.debts.collectAsState()

    val debt = debts.find {
        it.id == debtId
    }

    debt ?: return

    var amount by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Повернення боргу",
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
                    text = debt.title,
                    style =
                        MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        "Залишилось: ${debt.currentAmount} грн"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            label = {
                Text("Сума повернення")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val payment =
                    amount.toDoubleOrNull() ?: 0.0

                viewModel.returnDebt(
                    debt = debt,
                    amount = payment
                )

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Підтвердити")
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