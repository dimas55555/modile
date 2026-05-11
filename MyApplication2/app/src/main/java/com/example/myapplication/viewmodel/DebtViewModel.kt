package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.FakeData
import com.example.myapplication.model.Debt
import com.example.myapplication.model.Payment
import com.example.myapplication.model.Person

class DebtViewModel : ViewModel() {

    val persons = mutableStateListOf<Person>().apply {
        addAll(FakeData.persons)
    }

    val debts = mutableStateListOf<Debt>().apply {
        addAll(FakeData.debts)
    }

    val payments = mutableStateListOf<Payment>()

    fun addPersonWithDebt(
        person: Person,
        debt: Debt
    ) {
        persons.add(person)
        debts.add(debt)
    }

    fun deleteDebt(debt: Debt) {
        debts.remove(debt)
    }
    fun getDebtById(id: Int): Debt? {
        return debts.find { it.id == id }
    }

    fun getPersonById(id: Int): Person? {
        return persons.find { it.id == id }
    }

    fun returnDebt(
        debtId: Int,
        amount: Double
    ) {

        val debt = getDebtById(debtId) ?: return

        val newAmount = (debt.currentAmount - amount)
            .coerceAtLeast(0.0)

        val updatedDebt = debt.copy(
            currentAmount = newAmount,
            isReturned = newAmount == 0.0
        )

        val index = debts.indexOfFirst {
            it.id == debtId
        }

        if (index != -1) {
            debts[index] = updatedDebt
        }
        payments.add(
            Payment(
                id = payments.size + 1,
                debtId = debtId,
                amount = amount,
                paymentDate = System.currentTimeMillis(),
                isFullPayment = newAmount == 0.0
            )
        )
    }
}