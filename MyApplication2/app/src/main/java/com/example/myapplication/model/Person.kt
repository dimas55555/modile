package com.example.myapplication.model

/**
 * Представляє людину, яка має борг або якій винні гроші
 */
data class Person(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val isTrusted: Boolean,
    val createdAt: Long
)