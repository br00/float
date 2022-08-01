package com.alessandroborelli.floatapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.alessandroborelli.floatapp.presentation.FloatAppState
import com.alessandroborelli.floatapp.presentation.MainDestinations
import com.alessandroborelli.floatapp.presentation.home.HomeScreen
import com.alessandroborelli.floatapp.presentation.moorings.AddNewMooringScreen
import com.alessandroborelli.floatapp.presentation.moorings.MooringsScreen
import com.alessandroborelli.floatapp.presentation.moorings.MooringsViewModel
import com.alessandroborelli.floatapp.presentation.profile.ProfileScreen
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun FloatNavGraph(
    navController: NavHostController = rememberNavController(),
    appState: FloatAppState
) {
    val mooringsVewModel: MooringsViewModel = hiltViewModel()
    NavHost(navController, startDestination = MainDestinations.MAIN_ROUTE) {
        floatNavGraph(
            appState::navigateToAddNewMooringScreen,
            appState::upPress,
            mooringsVewModel
        )
    }
}

private fun NavGraphBuilder.floatNavGraph(
    onAddNewMooring: (NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    viewModel: MooringsViewModel
) {
    navigation(
        route = MainDestinations.MAIN_ROUTE,
        startDestination = Screen.Home.route
    ) {
        linkMainGraph(onAddNewMooring, viewModel)
    }
    composable(route = MainDestinations.ADD_MOORING_ROUTE) {
        AddNewMooringScreen(viewModel, upPress)
    }
}

internal fun NavGraphBuilder.linkMainGraph(
    onAddNewMooring: (NavBackStackEntry) -> Unit,
    viewModel: MooringsViewModel
) {
    composable(route = Screen.Home.route) {
        HomeScreen(title = Screen.Home.titleResId)
    }
    composable(route = Screen.Moorings.route) { from ->
        MooringsScreen(viewModel, onAddMooringClicked = { onAddNewMooring(from) } )
    }
    composable(route = Screen.Profile.route) {
        ProfileScreen(title = Screen.Profile.titleResId)
    }
}