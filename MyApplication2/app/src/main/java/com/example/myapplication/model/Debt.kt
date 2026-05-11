package com.example.myapplication.model

/**
 * Представляє запис про борг
 */
data class Debt(
    val id: Int,
    val personId: Int,
    val title: String,
    val initialAmount: Double,
    val currentAmount: Double,
    val isReturned: Boolean,
    val dueDate: Long
)