package com.example.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ResultScreen(navController: NavController, correct: String, wrong: String) {

    Column {

        Text("Результат")

        Text("Правильних: $correct")
        Text("Неправильних: $wrong")

        Button(onClick = {
            navController.navigate("list")
        }) {
            Text("До списку")
        }
    }
}