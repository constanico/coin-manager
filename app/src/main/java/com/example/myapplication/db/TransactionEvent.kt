package com.example.myapplication.db

import java.time.LocalDate

sealed interface TransactionEvent {
    object SaveTransaction: TransactionEvent
    data class SetFlow(val flow: String): TransactionEvent
    data class SetType(val type: String): TransactionEvent
    data class SetMethod(val method: String): TransactionEvent
    data class SetDate(val date: LocalDate): TransactionEvent
    data class SetDesc(val desc: String): TransactionEvent
    data class SetAmount(val amount: Int): TransactionEvent
    object ShowDialog: TransactionEvent
    object HideDialog: TransactionEvent
    data class SortTrx(val sortType: SortType): TransactionEvent
    data class DeleteTrx(val transaction: Transaction): TransactionEvent
}