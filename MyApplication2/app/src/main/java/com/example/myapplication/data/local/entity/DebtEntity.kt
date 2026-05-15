package com.example.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debts")
data class DebtEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val personId: Int,
    val title: String,
    val initialAmount: Double,
    val currentAmount: Double,
    val isReturned: Boolean,
    val dueDate: Long,
    val syncStatus: String
)