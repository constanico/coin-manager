package com.example.myapplication.graphs

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.BottomBarScreen
import com.example.myapplication.db.TransactionEvent
import com.example.myapplication.db.TransactionState
import com.example.myapplication.screens.AddTransactionScreen
import com.example.myapplication.screens.HomeScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController, state = state, onEvent = onEvent)
        }
        composable(route = BottomBarScreen.Profile.route) {
            Text(text = "Profile")
        }
        composable(route = Graph.ADD) {
            AddTransactionScreen(navController = navController, state = state, onEvent = onEvent)
        }
    }
}