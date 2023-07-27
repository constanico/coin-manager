package com.example.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val method: String,
    val date: String,
    val desc: String,
    val amount: Int,
        )