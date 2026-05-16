package com.example.myapplication

import com.example.myapplication.data.local.entity.DebtEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class DebtFilteringAndSortingTest {

    private val debts = listOf(

        DebtEntity(
            id = 1,
            personId = 1,
            title = "Іван",
            initialAmount = 1000.0,
            currentAmount = 300.0,
            isReturned = false,
            dueDate = 1,
            syncStatus = "synced"
        ),

        DebtEntity(
            id = 2,
            personId = 2,
            title = "Марія",
            initialAmount = 9000.0,
            currentAmount = 9000.0,
            isReturned = false,
            dueDate = 2,
            syncStatus = "pending"
        ),

        DebtEntity(
            id = 3,
            personId = 3,
            title = "Петро",
            initialAmount = 2000.0,
            currentAmount = 0.0,
            isReturned = true,
            dueDate = 3,
            syncStatus = "synced"
        )
    )

    @Test
    fun `filter active debts should exclude returned debts`() {
        val result = debts.filter {
            !it.isReturned
        }
        assertEquals(2, result.size)
    }

    @Test
    fun `search query should correctly filter by title`() {
        val result = debts.filter {
            it.title.contains(
                "Мар",
                ignoreCase = true
            )
        }

        assertEquals(
            "Марія",
            result.first().title
        )
    }

    @Test
    fun `sorting by amount should place largest debt first`() {
        val sorted =
            debts.sortedByDescending {
                it.currentAmount
            }

        assertEquals(
            "Марія",
            sorted.first().title
        )
    }
}