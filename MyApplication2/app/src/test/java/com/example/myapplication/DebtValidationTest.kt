package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class DebtValidationTest {

    @Test
    fun emptyName_shouldBeInvalid() {
        val name = ""
        val isValid =
            name.isNotBlank()
        assertFalse(isValid)
    }

    @Test
    fun invalidAmount_shouldReturnNull() {
        val amount = "abc"
        val parsed =
            amount.toDoubleOrNull()
        assertNull(parsed)
    }

    @Test
    fun validAmount_shouldParseCorrectly() {
        val amount = "2500.50"
        val parsed = amount.toDoubleOrNull()
        assertEquals(2500.50, parsed!!, 0.0)
    }
}