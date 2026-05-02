package com.example.myapplication.model

// Питання квізу
data class Question(
    val id: Int,
    val text: String,
    val isMultipleChoice: Boolean,
    val createdAt: String
)