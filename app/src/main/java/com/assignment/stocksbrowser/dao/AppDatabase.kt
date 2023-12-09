package com.assignment.stocksbrowser.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.stocksbrowser.model.room.EtfEntity
import com.assignment.stocksbrowser.model.room.StockEntity

@Database(entities = [StockEntity::class, EtfEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stocksDao(): StocksDao
    abstract fun etfDao(): EtfDao
}
