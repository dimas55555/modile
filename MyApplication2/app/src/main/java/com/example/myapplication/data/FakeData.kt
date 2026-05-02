package com.example.myapplication.data

import com.example.myapplication.model.*

object FakeData {

    val quizzes = listOf(
        Quiz(1, "Kotlin Basics", 2, true)
    )

    val questions = listOf(
        Question(1, "Що таке Kotlin?", false, "2026"),
        Question(2, "Що таке Android?", false, "2026")
    )

    val answersMap = mapOf(
        1 to listOf(
            AnswerOption(1, "Мова програмування", true, 2),
            AnswerOption(2, "Операційна система", false, 2)
        ),
        2 to listOf(
            AnswerOption(3, "Мобільна ОС", true, -1),
            AnswerOption(4, "Браузер", false, -1)
        )
    )
}