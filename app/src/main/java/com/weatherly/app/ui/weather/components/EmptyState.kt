package com.weatherly.app.ui.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.weatherly.app.R
import com.weatherly.app.ui.theme.AppTheme

@Composable
fun EmptyStateScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_city_selected),
            style = MaterialTheme.typography.h1,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.md))
        Text(
            text = stringResource(R.string.please_search_for_a_city),
            style = MaterialTheme.typography.h2,
            color = Color.Black
        )
    }
}
