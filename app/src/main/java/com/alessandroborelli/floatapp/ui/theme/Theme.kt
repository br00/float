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
    primary = canalWater200,
    primaryVariant = canalWater200,
    secondary = deepWater200
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

//private val LightColorPalette = lightColors(
//    primary = canalWater500,
//    primaryVariant = canalWater700,
//    secondary = deepWater200,
//    background = white,
//    surface = white,
//    onPrimary = dark,
//    onSecondary = light,
//    onBackground = dark,
//    onSurface = dark,
//)

private val LightColorScheme = lightColorScheme(
    primary = deepWater200,
    onPrimary = Color.White,
    primaryContainer = canalWater500,
    onPrimaryContainer = grey30,
    secondary = toChange,
    onSecondary = Color.White,
    secondaryContainer = canalWater500,
    onSecondaryContainer = grey10,
    tertiary = toChange2,
    onTertiary = Color.White,
    tertiaryContainer = Green90,
    onTertiaryContainer = Green10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = canalWater200,
    onBackground = grey10,
    surface = canalWater700,
    onSurface = grey10,
    surfaceVariant = canalWater500,
    onSurfaceVariant = grey10,
    outline = deepWater700
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