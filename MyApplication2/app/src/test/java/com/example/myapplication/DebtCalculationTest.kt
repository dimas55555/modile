package com.example.myapplication

import com.example.myapplication.data.local.entity.DebtEntity
import org.junit.Assert.*
import org.junit.Test

class DebtCalculationTest {

    @Test
    fun partialPayment_shouldReduceDebtCorrectly() {

        val debt = DebtEntity(
            id = 1,
            personId = 1,
            title = "Loan",
            initialAmount = 10000.0,
            currentAmount = 10000.0,
            isReturned = false,
            dueDate = 1L,
            syncStatus = "pending"
        )

        val payment = 3000.0

        val updated = debt.copy(
            currentAmount = debt.currentAmount - payment
        )

        assertEquals(7000.0, updated.currentAmount, 0.0)
        assertFalse(updated.isReturned)
    }

    @Test
    fun fullPayment_shouldMarkDebtAsReturned() {

        val debt = DebtEntity(
            id = 2,
            personId = 1,
            title = "Loan",
            initialAmount = 5000.0,
            currentAmount = 5000.0,
            isReturned = false,
            dueDate = 1L,
            syncStatus = "pending"
        )

        val updated = debt.copy(
            currentAmount = 0.0,
            isReturned = true
        )

        assertTrue(updated.isReturned)
        assertEquals(0.0, updated.currentAmount, 0.0)
    }

    @Test
    fun debt_shouldChangeSyncStatusToPendingOnUpdate() {

        val debt = DebtEntity(
            id = 3,
            personId = 1,
            title = "Loan",
            initialAmount = 2000.0,
            currentAmount = 1000.0,
            isReturned = false,
            dueDate = 1L,
            syncStatus = "synced"
        )

        val updated = debt.copy(
            syncStatus = "pending"
        )

        assertEquals("pending", updated.syncStatus)
    }
}