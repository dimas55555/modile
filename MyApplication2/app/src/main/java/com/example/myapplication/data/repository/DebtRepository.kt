package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.DebtDao
import com.example.myapplication.data.local.dao.PaymentDao
import com.example.myapplication.data.local.dao.PersonDao
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.data.local.entity.PaymentEntity
import com.example.myapplication.data.local.entity.PersonEntity
import com.example.myapplication.data.remote.FakeApiService
import kotlinx.coroutines.flow.Flow

class DebtRepository(

    private val debtDao: DebtDao,

    private val personDao: PersonDao,

    private val paymentDao: PaymentDao,

    private val api: FakeApiService
) {

    fun getAllDebts(): Flow<List<DebtEntity>> {
        return debtDao.getAllDebts()
    }

    suspend fun addDebt(debt: DebtEntity) {
        debtDao.insertDebt(debt)
    }

    suspend fun updateDebt(debt: DebtEntity) {
        debtDao.updateDebt(debt)
    }

    suspend fun deleteDebt(debt: DebtEntity) {
        debtDao.deleteDebt(debt)
    }

    suspend fun getDebtById(id: Int): DebtEntity? {
        return debtDao.getDebtById(id)
    }

    suspend fun getLastDebt(): DebtEntity? {
        return debtDao.getLastDebt()
    }

    suspend fun addPerson(person: PersonEntity) {
        personDao.insertPerson(person)
    }

    suspend fun getPersonById(id: Int): PersonEntity? {
        return personDao.getPersonById(id)
    }

    suspend fun addPayment(payment: PaymentEntity) {
        paymentDao.insertPayment(payment)
    }

    suspend fun updateDebtAmount(
        debtId: Int,
        newAmount: Double
    ) {

        val debt =
            debtDao.getDebtById(debtId)
                ?: return

        val updated = debt.copy(
            currentAmount = newAmount,
            isReturned = newAmount == 0.0,
            syncStatus = "synced"
        )

        debtDao.updateDebt(updated)
    }

    suspend fun syncDebts() {

        val serverDebts = api.fetchDebts()

        serverDebts.forEach {
            debtDao.insertDebt(it)
        }
    }
}