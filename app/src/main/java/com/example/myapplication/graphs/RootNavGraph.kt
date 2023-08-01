package com.example.myapplication.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.db.TransactionEvent
import com.example.myapplication.db.TransactionState
import com.example.myapplication.screens.MainScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN
    ) {
        composable(route = Graph.MAIN) {
            MainScreen(state = state, onEvent = onEvent)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
    const val ADD = "add_graph"
}