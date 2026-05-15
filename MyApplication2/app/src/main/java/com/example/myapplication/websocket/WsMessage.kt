package com.example.myapplication.websocket

import kotlinx.serialization.Serializable

@Serializable
data class WsMessage(

    val type: String,
    val debtId: Int,
    val newAmount: Double
)