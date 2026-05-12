package com.example.myapplication.websocket

import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FakeSocketServer {

    suspend fun startSending(
        latestDebtIdProvider: suspend () -> Int?,
        isActive: () -> Boolean,
        onMessage: (String) -> Unit
    ) {

        while (isActive()) {

            delay(5000)

            val latestDebtId =
                latestDebtIdProvider()
                    ?: continue

            val message = WsMessage(
                type = "DEBT_UPDATED",
                debtId = latestDebtId,
                newAmount = (1000..9000)
                    .random()
                    .toDouble()
            )

            onMessage(Json.encodeToString(message))
        }
    }
}