package com.example.myapplication

import app.cash.turbine.test
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.data.local.entity.PersonEntity
import com.example.myapplication.data.repository.DebtRepository
import com.example.myapplication.viewmodel.DebtViewModel
import com.example.myapplication.websocket.SocketState
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DebtViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var repository: DebtRepository
    private lateinit var fakeFlow: MutableStateFlow<List<DebtEntity>>

    private fun createViewModel(): DebtViewModel {
        return DebtViewModel(repository)
    }

    @Before
    fun setup() {
        fakeFlow = MutableStateFlow(emptyList())
        repository = mockk(relaxed = true)
        every { repository.getAllDebts() } returns fakeFlow
    }

    @Test
    fun `debts should emit empty list initially`() = runTest {
        val viewModel = createViewModel()
        viewModel.debts.test {
            assertEquals(emptyList<DebtEntity>(), awaitItem())
        }
    }

    @Test
    fun `addPersonWithDebt should call repository`() = runTest {
        val viewModel = createViewModel()
        val debt = DebtEntity(
            id = 0,
            personId = 1,
            title = "Test",
            initialAmount = 100.0,
            currentAmount = 100.0,
            isReturned = false,
            dueDate = System.currentTimeMillis(),
            syncStatus = "pending"
        )

        val person = mockk<PersonEntity>(relaxed = true)
        coEvery { repository.addDebt(any()) } just Runs
        coEvery { repository.addPerson(any()) } just Runs

        viewModel.addPersonWithDebt(person, debt)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.addPerson(person) }
        coVerify(exactly = 1) {
            repository.addDebt(match {
                it.title == "Test" &&
                        it.personId == 1 &&
                        it.initialAmount == 100.0
            })
        }
    }

    @Test
    fun `socket state should exist`() = runTest {
        val viewModel = createViewModel()
        viewModel.socketState.test {
            assertEquals(SocketState.Disconnected, awaitItem())
        }
    }
}