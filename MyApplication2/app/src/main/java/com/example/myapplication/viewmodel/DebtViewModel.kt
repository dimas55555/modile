package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.data.local.entity.PaymentEntity
import com.example.myapplication.data.local.entity.PersonEntity
import com.example.myapplication.data.repository.DebtRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DebtViewModel(
    private val repository: DebtRepository
) : ViewModel() {

    val debts = repository
        .getAllDebts()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {

        viewModelScope.launch {
            repository.syncDebts()
        }
    }

    fun addPersonWithDebt(
        person: PersonEntity,
        debt: DebtEntity
    ) {

        viewModelScope.launch {

            repository.addPerson(person)

            repository.addDebt(debt)
        }
    }

    fun deleteDebt(debt: DebtEntity) {

        viewModelScope.launch {

            repository.deleteDebt(debt)
        }
    }

    fun returnDebt(
        debt: DebtEntity,
        amount: Double
    ) {

        viewModelScope.launch {

            val newAmount =
                (debt.currentAmount - amount)
                    .coerceAtLeast(0.0)

            val updatedDebt = debt.copy(
                currentAmount = newAmount,
                isReturned = newAmount == 0.0,
                syncStatus = "pending"
            )

            repository.deleteDebt(debt)

            repository.addDebt(updatedDebt)

            repository.addPayment(
                PaymentEntity(
                    debtId = debt.id,
                    amount = amount,
                    paymentDate =
                        System.currentTimeMillis(),
                    isFullPayment =
                        newAmount == 0.0
                )
            )
        }
    }
}