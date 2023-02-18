package com.dicedev.stockapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dicedev.stockapp.presentation.company_listings.CompanyListingsScreen

@Composable
fun AppNavigation(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = AppScreens.CompanyListingsScreen.name,
        builder = {
            composable(AppScreens.CompanyListingsScreen.name) {
                CompanyListingsScreen(navController = navHostController)
            }
        }
    )
}