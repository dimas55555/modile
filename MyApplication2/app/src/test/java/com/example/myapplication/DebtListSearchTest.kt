package com.example.myapplication

import com.example.myapplication.data.local.entity.DebtEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class DebtListSearchTest {

    @Test
    fun search_shouldBeCaseInsensitive() {
        val debts = listOf(
            DebtEntity(
                id = 1,
                personId = 1,
                title = "Oleksandr",
                initialAmount = 100.0,
                currentAmount = 100.0,
                isReturned = false,
                dueDate = 1L,
                syncStatus = "pending"
            )
        )

        val result = debts.filter {

            it.title.contains(
                "olek",
                ignoreCase = true
            )
        }

        assertEquals(1, result.size)
    }
}