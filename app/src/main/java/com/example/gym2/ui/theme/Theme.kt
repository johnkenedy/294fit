package com.example.gym2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val darkColorPalette =  darkColors(
    primary = darkBlue,
    primaryVariant = lightBlue,
    secondary = holoGreen
)

private val lightColorPalette = lightColors(
    primary = darkBlue,
    primaryVariant = lightBlue,
    secondary = holoGreen
)

@Composable
fun Gym2Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) darkColorPalette else lightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}