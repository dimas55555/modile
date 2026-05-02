package com.example.myapplication.model

// Опис квізу
data class Quiz(
    val id: Int,
    val title: String,
    val questionCount: Int,
    val isActive: Boolean
)