package com.example.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.myapplication.data.FakeData

@Composable
fun QuestionScreen(navController: NavController) {

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var correctCount by remember { mutableStateOf(0) }
    var wrongCount by remember { mutableStateOf(0) }

    val questions = FakeData.questions
    val question = questions[currentQuestionIndex]
    val answers = FakeData.answersMap[question.id] ?: emptyList()

    Column {

        Text(text = question.text)

        answers.forEach { answer ->
            Button(onClick = {

                if (answer.isCorrect) {
                    correctCount++
                } else {
                    wrongCount++
                }

                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                } else {
                    navController.navigate("result/$correctCount/$wrongCount")
                }

            }) {
                Text(answer.text)
            }
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Назад")
        }
    }
}