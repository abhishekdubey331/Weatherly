package com.weatherly.app.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.weatherly.app.R

object AppTheme {
    val spacing = Spacing()
    val fontFamily = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal)
    )

    // Spacing values used throughout the app
    data class Spacing(
        val oneDp: Dp = 1.dp,
        val xs: Dp = 4.dp,
        val sm: Dp = 8.dp,
        val md: Dp = 16.dp,
        val lg: Dp = 24.dp,
        val xl: Dp = 32.dp,
        val xxl: Dp = 48.dp
    )

    object IconSize {
        val weatherIconSize = 120.dp
    }
}

private val White = Color(0xFFFFFFFF)
private val LightGray = Color(0xFFF0F0F0)
private val Purple = Color(0xFFBB86FC)
private val DeepPurple = Color(0xFFBB86FC)


private val LightColors = lightColors(
    primary = White,
    primaryVariant = LightGray,
    onPrimary = Color.Black,
    secondary = Purple,
    secondaryVariant = DeepPurple,
    onSecondary = Color.White,
    background = White,
    surface = White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors, typography = Typography(
            defaultFontFamily = AppTheme.fontFamily
        ), content = content
    )
}
