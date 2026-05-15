package com.example.myapplication.data.remote

import com.example.myapplication.data.local.entity.DebtEntity
import kotlinx.coroutines.delay

class FakeApiService {

    suspend fun fetchDebts(): List<DebtEntity> {

        delay(2500)

        return emptyList()
    }
}