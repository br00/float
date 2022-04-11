package com.alessandroborelli.floatapp.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.alessandroborelli.floatapp.presentation.navigation.FloatBottomNavigation
import com.alessandroborelli.floatapp.presentation.navigation.FloatNavGraph
import com.alessandroborelli.floatapp.ui.theme.FloatTheme

@Composable
fun FloatApp() {
    FloatTheme {
        val appState = rememberFloatAppState()
        Scaffold(
            bottomBar = { FloatBottomNavigation(navController = appState.navController) }
        ) {
            FloatNavGraph(navController = appState.navController)
        }
    }
}