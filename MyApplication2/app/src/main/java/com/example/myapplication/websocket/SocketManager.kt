package com.example.myapplication.websocket

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

class SocketManager {

    private val scope =
        CoroutineScope(Dispatchers.IO)

    private val fakeServer =
        FakeSocketServer()

    private var socketJob: Job? = null

    private var messageHandler:
            ((WsMessage) -> Unit)? = null

    private val _socketState =
        MutableStateFlow(SocketState.Disconnected)

    val socketState: StateFlow<SocketState>
        get() = _socketState

    fun connect(
        latestDebtIdProvider: suspend () -> Int?
    ) {

        if (_socketState.value == SocketState.Connected ||
            _socketState.value == SocketState.Connecting
        ) return

        _socketState.value = SocketState.Connecting

        socketJob = scope.launch {

            delay(1000)

            _socketState.value = SocketState.Connected

            try {

                fakeServer.startSending(
                    latestDebtIdProvider,
                    isActive = { socketJob?.isActive == true }
                ) {
                    val parsed =
                        Json.decodeFromString<WsMessage>(it)

                    messageHandler?.invoke(parsed)
                }

            } catch (e: CancellationException) {
            } catch (e: Exception) {
                reconnect(latestDebtIdProvider)
            }
        }
    }

    private fun reconnect(
        latestDebtIdProvider: suspend () -> Int?
    ) {
        _socketState.value = SocketState.Reconnecting
        socketJob = scope.launch {
            delay(3000)
            connect(latestDebtIdProvider)
        }
    }

    fun disconnect() {
        socketJob?.cancel()
        socketJob = null
        _socketState.value = SocketState.Disconnected
    }

    fun onMessage(
        handler: (WsMessage) -> Unit
    ) {
        messageHandler = handler
    }
}