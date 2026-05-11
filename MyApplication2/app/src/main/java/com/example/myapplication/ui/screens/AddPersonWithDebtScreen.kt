package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.model.Debt
import com.example.myapplication.model.Person
import com.example.myapplication.viewmodel.DebtViewModel

@Composable
fun AddPersonWithDebtScreen(
    navController: NavController,
    viewModel: DebtViewModel
) {

    var personName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var debtTitle by remember { mutableStateOf("") }
    var debtAmount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Нова людина та борг",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = personName,
            onValueChange = { personName = it },
            label = { Text("Ім'я") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Телефон") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = debtTitle,
            onValueChange = { debtTitle = it },
            label = { Text("Назва боргу") }
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = debtAmount,
            onValueChange = { debtAmount = it },
            label = { Text("Сума") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val personId = viewModel.persons.size + 1
                val debtId = viewModel.debts.size + 1

                val person = Person(
                    id = personId,
                    name = personName,
                    phone = phone,
                    email = email,
                    isTrusted = true,
                    createdAt = System.currentTimeMillis()
                )
                val debt = Debt(
                    id = debtId,
                    personId = personId,
                    title = debtTitle,
                    initialAmount = debtAmount.toDoubleOrNull() ?: 0.0,
                    currentAmount = debtAmount.toDoubleOrNull() ?: 0.0,
                    isReturned = false,
                    dueDate = System.currentTimeMillis()
                )

                viewModel.addPersonWithDebt(person, debt)

                navController.popBackStack()
            }
        ) {
            Text("Зберегти")
        }
    }
}