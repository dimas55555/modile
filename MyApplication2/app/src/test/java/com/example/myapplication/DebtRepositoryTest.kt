package com.example.myapplication

import com.example.myapplication.data.local.dao.DebtDao
import com.example.myapplication.data.local.dao.PersonDao
import com.example.myapplication.data.local.dao.PaymentDao
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.data.remote.FakeApiService
import com.example.myapplication.data.repository.DebtRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class DebtRepositoryTest {

    private lateinit var debtDao: DebtDao
    private lateinit var personDao: PersonDao
    private lateinit var paymentDao: PaymentDao
    private lateinit var api: FakeApiService
    private lateinit var repository: DebtRepository

    @Before
    fun setup() {
        debtDao = mockk(relaxed = true)
        personDao = mockk(relaxed = true)
        paymentDao = mockk(relaxed = true)
        api = mockk(relaxed = true)

        repository = DebtRepository(
            debtDao,
            personDao,
            paymentDao,
            api
        )
    }

    @Test
    fun `updateDebtAmount updates current amount correctly`() = runTest {

        val debt = DebtEntity(
            id = 1,
            personId = 10,
            title = "Test Debt",
            initialAmount = 1000.0,
            currentAmount = 1000.0,
            isReturned = false,
            dueDate = System.currentTimeMillis(),
            syncStatus = "pending"
        )

        coEvery { debtDao.getDebtById(1) } returns debt
        coEvery { debtDao.updateDebt(any()) } just Runs

        repository.updateDebtAmount(1, 400.0)

        coVerify {
            debtDao.updateDebt(match {
                it.id == 1 &&
                        it.currentAmount == 400.0 &&
                        it.isReturned == false
            })
        }
    }

    @Test
    fun `syncDebts inserts server debts into local db`() = runTest {

        val serverData = listOf(
            DebtEntity(
                id = 1,
                personId = 1,
                title = "A",
                initialAmount = 100.0,
                currentAmount = 100.0,
                isReturned = false,
                dueDate = 123L,
                syncStatus = "synced"
            ),
            DebtEntity(
                id = 2,
                personId = 2,
                title = "B",
                initialAmount = 200.0,
                currentAmount = 200.0,
                isReturned = false,
                dueDate = 456L,
                syncStatus = "synced"
            )
        )

        coEvery { api.fetchDebts() } returns serverData
        coEvery { debtDao.insertDebt(any()) } just Runs
        repository.syncDebts()
        coVerify(exactly = 2) {
            debtDao.insertDebt(any())
        }
    }
}