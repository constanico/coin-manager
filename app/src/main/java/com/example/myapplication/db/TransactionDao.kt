package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Upsert
    suspend fun upsertTrx(transaction: Transaction)

    @Delete
    suspend fun deleteTrx(transaction: Transaction)

    @Query("SELECT * FROM `transaction` ORDER BY id ASC")
    fun getTrxOrderedByFirstId(): Flow<List<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY id DESC")
    fun getTrxOrderedByLastId(): Flow<List<Transaction>>
}