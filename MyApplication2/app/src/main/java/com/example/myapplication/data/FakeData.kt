package com.example.myapplication.data

import com.example.myapplication.model.Debt
import com.example.myapplication.model.Person

object FakeData {

    val persons = mutableListOf(

        Person(
            id = 1,
            name = "Іван Петренко",
            phone = "+380991112233",
            email = "ivan@gmail.com",
            isTrusted = true,
            createdAt = System.currentTimeMillis()
        ),

        Person(
            id = 2,
            name = "Марія Коваль",
            phone = "+380671234567",
            email = "maria@gmail.com",
            isTrusted = false,
            createdAt = System.currentTimeMillis()
        )
    )

    val debts = mutableListOf(

        Debt(
            id = 1,
            personId = 1,
            title = "Позика на ноутбук",
            initialAmount = 12000.0,
            currentAmount = 8500.0,
            isReturned = false,
            dueDate = System.currentTimeMillis()
        ),

        Debt(
            id = 2,
            personId = 2,
            title = "Кафе та вечеря",
            initialAmount = 2500.0,
            currentAmount = 0.0,
            isReturned = true,
            dueDate = System.currentTimeMillis()
        )
    )
}