package com.example.crypto2023.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crypto2023.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class],version=1,exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        val DB_NAME = "main.db"
        private val Lock = Any()
        fun getInstance(context: Context): AppDatabase {
            synchronized(Lock) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }
    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}