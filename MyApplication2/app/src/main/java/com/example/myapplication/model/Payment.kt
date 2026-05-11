package com.example.myapplication.model

/**
 * Представляє оплату або часткове погашення боргу
 */
data class Payment(
    val id: Int,
    val debtId: Int,
    val amount: Double,
    val paymentDate: Long,
    val isFullPayment: Boolean
)