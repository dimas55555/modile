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

    var amount by remember {
        mutableStateOf("")
    }

    val debt = viewModel.getDebtById(debtId)

    debt ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Повернення боргу",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Поточний борг: ${debt.currentAmount}")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            label = {
                Text("Сума повернення")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val paymentAmount =
                    amount.toDoubleOrNull() ?: 0.0

                viewModel.returnDebt(
                    debtId,
                    paymentAmount
                )

                navController.popBackStack()
            }
        ) {
            Text("Повернути")
        }
    }
}