package com.alessandroborelli.floatapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = canalWater200,
    primaryVariant = canalWater200,
    secondary = deepWater200
)

private val LightColorPalette = lightColors(
    primary = canalWater500,
    primaryVariant = canalWater700,
    secondary = deepWater200,
    background = white,
    surface = white,
    onPrimary = dark,
    onSecondary = light,
    onBackground = dark,
    onSurface = dark,
)

@Composable
fun FloatTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

//TODO move to shapes?
val BottomSheetShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)