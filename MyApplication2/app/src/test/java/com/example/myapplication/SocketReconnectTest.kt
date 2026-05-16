package com.example.myapplication

import com.example.myapplication.websocket.SocketState
import org.junit.Assert.assertEquals
import org.junit.Test

class SocketReconnectTest {
    @Test
    fun reconnectState_shouldBeAssigned() {
        val state =
            SocketState.Reconnecting
        assertEquals(
            SocketState.Reconnecting,
            state
        )
    }
}