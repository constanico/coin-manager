package com.example.myapplication.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionViewModel(private val dao: TransactionDao): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.LAST_ID)
    private val _transactions = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.FIRST_ID -> dao.getTrxOrderedByFirstId()
                SortType.LAST_ID -> dao.getTrxOrderedByLastId()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(TransactionState())
    val state = combine(_state, _sortType, _transactions) { state, sortType, transactions ->
        state.copy(
            transactions = transactions,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionState())

    fun onEvent(event: TransactionEvent) {
        when(event) {
            is TransactionEvent.DeleteTrx -> {
                viewModelScope.launch {
                    dao.deleteTrx(event.transaction)
                }
            }
            TransactionEvent.HideDialog -> {
                _state.update {it.copy(
                    isAddingTrx = false
                )}
            }
            TransactionEvent.SaveTransaction -> {
                val flow = state.value.flow
                val type = state.value.type
                val method = state.value.method
                val date = state.value.date
                val desc = state.value.desc
                val amount = state.value.amount

                if(method.isBlank() || desc.isBlank()) {
                    return
                }

                val transaction = Transaction(
                    flow = flow,
                    type = type,
                    method = method,
                    date = date,
                    desc = desc,
                    amount = amount
                )
                viewModelScope.launch {
                    dao.upsertTrx(transaction)
                }
                _state.update { it.copy(
                    isAddingTrx = false,
                    flow = "",
                    type = "",
                    method = "",
                    date = LocalDate.now(),
                    desc = "",
                    amount = 0
                ) }
            }
            is TransactionEvent.SetAmount -> {
                _state.update { it.copy(
                    amount = event.amount
                ) }
            }
            is TransactionEvent.SetDate -> {
                _state.update { it.copy(
                    date = event.date
                ) }
            }
            is TransactionEvent.SetDesc -> {
                _state.update { it.copy(
                    desc = event.desc
                ) }
            }
            is TransactionEvent.SetMethod -> {
                _state.update { it.copy(
                    method = event.method
                ) }
            }
            is TransactionEvent.SetType -> {
                _state.update { it.copy(
                    type = event.type
                ) }
            }
            TransactionEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingTrx = true
                ) }
            }
            is TransactionEvent.SortTrx -> {
                _sortType.value = event.sortType
            }
            is TransactionEvent.SetFlow -> {
                _state.update {it.copy(
                    flow = event.flow
                ) }
            }
        }
    }
}