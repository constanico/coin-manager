package com.example.myapplication.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Parcelize
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val method: String,
    val date: LocalDate,
    val desc: String,
    val amount: Int,
    ): Parcelable {
    val createdDateFormatted: String
        get() = date.format(DateTimeFormatter.ofPattern("E, dd MMM yyyy"))
}