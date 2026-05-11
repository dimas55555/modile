package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.navigation.NavRoutes
import com.example.myapplication.viewmodel.DebtViewModel

@Composable
fun DebtListScreen(
    navController: NavController,
    viewModel: DebtViewModel
) {

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

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(viewModel.debts) { debt ->

                val person =
                    viewModel.getPersonById(debt.personId)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable {
                            navController.navigate(
                                "detail/${debt.id}"
                            )
                        }
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = person?.name ?: ""
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(text = debt.title)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Залишилось: ${debt.currentAmount}"
                        )

                        Text(
                            text = "Початково: ${debt.initialAmount}"
                        )

                        Text(
                            text = if (debt.isReturned)
                                "Погашено"
                            else
                                "Активний"
                        )
                    }
                }
            }
        }
    }
}