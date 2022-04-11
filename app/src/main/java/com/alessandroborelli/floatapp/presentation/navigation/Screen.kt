package com.alessandroborelli.floatapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsBoat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person

import androidx.compose.ui.graphics.vector.ImageVector
import com.alessandroborelli.floatapp.R

sealed class Screen(val route: String, @StringRes val titleResId: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Outlined.Home)
    object Moorings : Screen("moorings", R.string.moorings, Icons.Outlined.DirectionsBoat)
    object Profile : Screen("profile", R.string.profile, Icons.Outlined.Person)
}