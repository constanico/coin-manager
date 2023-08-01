package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.db.TransactionEvent
import com.example.myapplication.db.TransactionState
import com.example.myapplication.db.TransactionViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = "Home")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add_graph")
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xffE3F2FD)
                )
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listOf(state.date)) {
                index ->
                Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                            .heightIn(max = 500.dp)
                    ) {
                        Text(text = "${index.dayOfMonth} ${index.month} ${index.year}")
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
//                            itemsIndexed(listOf(1, 2, 3)) { index, item ->
//                                Text(text = "Test, 123")
//                                Text(text = "14000, BCA")
////                                Divider(color = Color.Black)
//                            }
                            items(state.transactions) { transaction ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "${transaction.type} ${transaction.desc}",
                                            fontSize = 20.sp
                                        )
                                        Text(
                                            text = "${transaction.amount} ${transaction.method}",
                                            fontSize = 12.sp
                                        )
                                    }
//                                    IconButton(
//                                        onClick = { onEvent(TransactionEvent.DeleteTrx(transaction)) }
//                                    ) {
//                                        Icon(
//                                            imageVector = Icons.Default.Delete,
//                                            contentDescription = "Delete Transaction"
//                                        )
//                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.transactions) { transaction ->
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${transaction.type} ${transaction.desc}",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "${transaction.amount} ${transaction.method}",
                        fontSize = 12.sp
                    )
                }
                IconButton(
                    onClick = { onEvent(TransactionEvent.DeleteTrx(transaction)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Transaction"
                    )
                }
            }
        }
    }
}