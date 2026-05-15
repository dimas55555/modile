package com.example.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class PaymentEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val debtId: Int,
    val amount: Double,
    val paymentDate: Long,
    val isFullPayment: Boolean
)