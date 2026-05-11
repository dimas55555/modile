package com.example.myapplication.data.remote

import com.example.myapplication.data.local.entity.DebtEntity
import kotlinx.coroutines.delay

/*
API CONTRACT

1)
GET /debts

Повертає:
- список всіх боргів

Response:
List<DebtEntity>


2)
POST /debts

Створює новий борг

Request body:
{
    personId: Int,
    title: String,
    initialAmount: Double,
    currentAmount: Double,
    isReturned: Boolean,
    dueDate: Long,
    syncStatus: String
}

Повертає:
- створений DebtEntity


3)
DELETE /debts/{id}

Видаляє борг за id

Path params:
- id: Int

Повертає:
- статус успішного видалення


4)
GET /persons/{id}

Повертає інформацію про людину

Path params:
- id: Int

Повертає:
PersonEntity
*/

class FakeApiService {

    suspend fun fetchDebts(): List<DebtEntity> {

        delay(250)

        return emptyList()
    }
}