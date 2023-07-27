package com.example.myapplication.db

data class TransactionState(
    val transactions: List<Transaction> = emptyList(),
    val type: String = "",
    val method: String = "",
    val date: String = "",
    val desc: String = "",
    val amount: Int = 0,
    val isAddingTrx: Boolean = false,
    val sortType: SortType = SortType.FIRST_ID
)
