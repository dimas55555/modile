package com.example.myapplication

import com.example.myapplication.websocket.SocketManager
import com.example.myapplication.websocket.SocketState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SocketManagerTest {

    @Test
    fun `initial state should be Disconnected`() {
        val manager = SocketManager()
        assertEquals(
            SocketState.Disconnected,
            manager.socketState.value
        )
    }

    @Test
    fun `disconnect should set Disconnected state`() = runTest {

        val manager = SocketManager()
        manager.disconnect()
        assertEquals(
            SocketState.Disconnected,
            manager.socketState.value
        )
    }
}