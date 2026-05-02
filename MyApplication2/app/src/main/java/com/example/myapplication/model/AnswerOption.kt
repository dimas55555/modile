package com.example.myapplication.model

data class AnswerOption(
    val id: Int,
    val text: String,
    val isCorrect: Boolean,
    val nextQuestionId: Int
)