package com.alessandroborelli.floatapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsBoat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person

import androidx.compose.ui.graphics.vector.ImageVector
import com.alessandroborelli.floatapp.R
import com.alessandroborelli.floatapp.presentation.MainDestinations

sealed class Screen(val route: String, @StringRes val titleResId: Int, val icon: ImageVector) {
    object Home : Screen("${MainDestinations.MAIN_ROUTE}/home", R.string.home, Icons.Outlined.Home)
    object Moorings : Screen("${MainDestinations.MAIN_ROUTE}/moorings", R.string.moorings, Icons.Outlined.DirectionsBoat)
    object Profile : Screen("${MainDestinations.MAIN_ROUTE}/profile", R.string.profile, Icons.Outlined.Person)
}