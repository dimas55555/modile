package com.example.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun QuizDetailScreen(navController: NavController, quizId: String) {

    Column {
        Text("Деталі квізу ID: $quizId")

        Button(onClick = {
            navController.navigate("question")
        }) {
            Text("Почати квіз")
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Назад")
        }
    }
}