package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.data.local.entity.PersonEntity
import com.example.myapplication.viewmodel.DebtViewModel

@Composable
fun AddPersonWithDebtScreen(
    navController: NavController,
    viewModel: DebtViewModel
) {

    var name by remember { mutableStateOf("") }

    var phone by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var debtTitle by remember {
        mutableStateOf("")
    }

    var debtAmount by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Нова людина та борг",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Ім'я")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
            },
            label = {
                Text("Телефон")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = debtTitle,
            onValueChange = {
                debtTitle = it
            },
            label = {
                Text("Назва боргу")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = debtAmount,
            onValueChange = {
                debtAmount = it
            },
            label = {
                Text("Сума")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val person = PersonEntity(
                    name = name,
                    phone = phone,
                    email = email,
                    isTrusted = true,
                    createdAt =
                        System.currentTimeMillis()
                )

                val debt = DebtEntity(
                    personId = 1,
                    title = debtTitle,
                    initialAmount =
                        debtAmount.toDoubleOrNull()
                            ?: 0.0,
                    currentAmount =
                        debtAmount.toDoubleOrNull()
                            ?: 0.0,
                    isReturned = false,
                    dueDate =
                        System.currentTimeMillis(),
                    syncStatus = "pending"
                )

                viewModel.addPersonWithDebt(
                    person,
                    debt
                )

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Зберегти")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Скасувати")
        }
    }
}