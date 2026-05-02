package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.myapplication.screens.*

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            QuizListScreen(navController)
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "0"
            QuizDetailScreen(navController, id)
        }

        composable("question") {
            QuestionScreen(navController)
        }

        // 🔥 НОВИЙ ЕКРАН РЕЗУЛЬТАТУ
        composable("result/{correct}/{wrong}") { backStackEntry ->
            val correct = backStackEntry.arguments?.getString("correct") ?: "0"
            val wrong = backStackEntry.arguments?.getString("wrong") ?: "0"
            ResultScreen(navController, correct, wrong)
        }
    }
}