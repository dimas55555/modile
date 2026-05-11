package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.dao.DebtDao
import com.example.myapplication.data.local.dao.PaymentDao
import com.example.myapplication.data.local.dao.PersonDao
import com.example.myapplication.data.local.entity.DebtEntity
import com.example.myapplication.data.local.entity.PaymentEntity
import com.example.myapplication.data.local.entity.PersonEntity

@Database(
    entities = [
        PersonEntity::class,
        DebtEntity::class,
        PaymentEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun debtDao(): DebtDao

    abstract fun personDao(): PersonDao

    abstract fun paymentDao(): PaymentDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "debt_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}