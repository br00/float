package com.alessandroborelli.floatapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = grey20,
    primaryVariant = grey30,
    onPrimary = grey50,

    secondary = blueWater,
    onSecondary = grey10,

    background = grey10,
    onBackground = grey80,

    surface = grey10,
    onSurface = grey80,

    error = Red40,
    onError = Color.White,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = canalWater200,
    primaryVariant = canalWater500,
    onPrimary = grey30,

    secondary = deepWater200,
    onSecondary = light,

    background = canalWater700,
    onBackground = grey30,

    surface = white,
    onSurface = grey10,

    error = Red40,
    onError = Color.White,
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