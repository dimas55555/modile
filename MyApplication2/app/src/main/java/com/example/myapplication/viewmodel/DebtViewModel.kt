package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.data.local.entity.PaymentEntity
import com.example.myapplication.data.local.entity.PersonEntity
import com.example.myapplication.data.repository.DebtRepository
import com.example.myapplication.websocket.SocketManager
import com.example.myapplication.websocket.WsMessage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DebtViewModel(
    private val repository: DebtRepository
) : ViewModel() {

    private val socketManager =
        SocketManager()

    val socketState =
        socketManager.socketState

    val debts = repository
        .getAllDebts()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun connectSocket() {

        socketManager.connect {

            repository.getLastDebt()?.id
        }

        socketManager.onMessage { message ->

            handleSocketMessage(message)
        }
    }

    fun disconnectSocket() {

        socketManager.disconnect()
    }

    private fun handleSocketMessage(
        message: WsMessage
    ) {

        viewModelScope.launch {

            when (message.type) {

                "DEBT_UPDATED" -> {

                    repository.updateDebtAmount(
                        debtId = message.debtId,
                        newAmount = message.newAmount
                    )
                }
            }
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

            repository.updateDebt(updatedDebt)

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

    fun clearReturnedDebts() {

        viewModelScope.launch {

            repository.clearReturnedDebts()
        }
    }

    override fun onCleared() {

        super.onCleared()

        socketManager.disconnect()
    }
}