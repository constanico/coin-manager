package com.example.myapplication.graphs

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.BottomBarScreen
import com.example.myapplication.HomeMenuScreen
import com.example.myapplication.db.TransactionEvent
import com.example.myapplication.db.TransactionState
import com.example.myapplication.screens.AddTransactionDialog

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = HomeMenuScreen.Add.route) {
            AddTransactionDialog(navController = navController, state = state, onEvent = onEvent)
        }
        composable(route = HomeMenuScreen.Edit.route) {
            Text(text = "Edit")
        }
    }
}