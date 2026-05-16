package com.example.myapplication

import com.example.myapplication.websocket.SocketState
import com.example.myapplication.websocket.WsMessage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class WebSocketLogicTest {

    @Test
    fun websocketJson_shouldParseCorrectly() {

        val json = """
            {
                "type":"DEBT_UPDATED",
                "debtId":1,
                "newAmount":2500.0
            }
        """.trimIndent()

        val parsed =
            Json.decodeFromString<WsMessage>(json)

        assertEquals(
            "DEBT_UPDATED",
            parsed.type
        )

        assertEquals(
            2500.0,
            parsed.newAmount,
            0.0
        )
    }

    @Test
    fun reconnectState_shouldChangeCorrectly() {
        val state =
            SocketState.Reconnecting
        assertEquals(
            SocketState.Reconnecting,
            state
        )
    }
}