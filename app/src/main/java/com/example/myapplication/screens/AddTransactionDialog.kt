package com.example.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.db.TransactionEvent
import com.example.myapplication.db.TransactionState

@Composable
fun AddTransactionDialog(
    navController: NavHostController,
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { padding ->
        Column(
            Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = state.type,
                onValueChange = {
                    onEvent(TransactionEvent.SetType(it))
                },
                placeholder = {
                    Text(text = "Type")
                }
            )
            TextField(
                value = state.method,
                onValueChange = {
                    onEvent(TransactionEvent.SetMethod(it))
                },
                placeholder = {
                    Text(text = "Method")
                }
            )
            TextField(
                value = state.date,
                onValueChange = {
                    onEvent(TransactionEvent.SetDate(it))
                },
                placeholder = {
                    Text(text = "Date")
                }
            )
            TextField(
                value = state.desc,
                onValueChange = {
                    onEvent(TransactionEvent.SetDesc(it))
                },
                placeholder = {
                    Text(text = "Desc")
                }
            )
//                TextField(
//                    value = state.amount,
//                    onValueChange = {
//                        onEvent(TransactionEvent.SetAmount(it))
//                    },
//                    placeholder = {
//                        Text(text = "Amount")
//                    },
//                ),
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = { onEvent(TransactionEvent.SaveTransaction) }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}