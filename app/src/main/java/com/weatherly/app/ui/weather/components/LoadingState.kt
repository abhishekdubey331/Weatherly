package com.weatherly.app.ui.weather.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.weatherly.app.ui.theme.AppTheme

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppTheme.spacing.lg),
        contentAlignment = Alignment.TopCenter
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}
