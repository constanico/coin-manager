package com.example.myapplication.db

import java.time.LocalDate

data class TransactionState(
    val transactions: List<Transaction> = emptyList(),
    val type: String = "",
    val method: String = "",
    val date: LocalDate = LocalDate.now(),
    val desc: String = "",
    val amount: Int = 0,
    val isAddingTrx: Boolean = false,
    val sortType: SortType = SortType.LAST_ID
)
