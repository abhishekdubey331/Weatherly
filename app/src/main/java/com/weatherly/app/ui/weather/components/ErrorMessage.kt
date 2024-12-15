package com.weatherly.app.ui.weather.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.weatherly.app.ui.theme.AppTheme

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.lg),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.body1.copy(color = Color.Red),
            textAlign = TextAlign.Center
        )
    }
}
