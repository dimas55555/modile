package com.example.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myapplication.data.FakeData

@Composable
fun QuizListScreen(navController: NavController) {

    Column {
        Text("Список квізів")

        FakeData.quizzes.forEach { quiz ->
            Button(onClick = {
                navController.navigate("detail/${quiz.id}")
            }) {
                Text(quiz.title)
            }
        }
    }
}