package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.db.Transaction

@Database(
    entities = [Transaction::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TransactionDatabase: RoomDatabase() {
    abstract val dao: TransactionDao
}