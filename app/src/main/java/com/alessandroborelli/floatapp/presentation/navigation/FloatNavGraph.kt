package com.alessandroborelli.floatapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alessandroborelli.floatapp.presentation.home.HomeScreen
import com.alessandroborelli.floatapp.presentation.moorings.MooringsScreen
import com.alessandroborelli.floatapp.presentation.profile.ProfileScreen

@Composable
fun FloatNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(title = Screen.Home.titleResId)
        }
        composable(route = Screen.Moorings.route) {
            MooringsScreen(title = Screen.Moorings.titleResId)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(title = Screen.Profile.titleResId)
        }
    }
}