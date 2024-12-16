package com.weatherly.app.ui.weather.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.weatherly.app.ui.theme.AppTheme

@Composable
fun WeatherCard(
    cityName: String,
    temperature: String,
    weatherIconUrl: String,
    onWeatherDataClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.md, vertical = AppTheme.spacing.sm)
            .clickable(onClick = onWeatherDataClicked),
        border = BorderStroke(AppTheme.spacing.oneDp, Color.LightGray),
        shape = RoundedCornerShape(AppTheme.spacing.md),
        elevation = AppTheme.spacing.xs,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        WeatherCardContent(cityName, temperature, weatherIconUrl)
    }
}

@Composable
fun WeatherCardContent(
    cityName: String,
    temperature: String,
    weatherIconUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.xs)
        ) {
            Text(
                text = cityName,
                style = cardTitle
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
            Text(
                text = "$temperature°",
                style = cardTemperature
            )
        }
        WeatherIconImage(weatherIconUrl)
    }
}

private val cardTitle = TextStyle(
    fontFamily = AppTheme.fontFamily,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold,
    color = Color.Black
)
private val cardTemperature = TextStyle(
    fontFamily = AppTheme.fontFamily,
    fontSize = 42.sp,
    fontWeight = FontWeight.Bold,
    color = Color.Black
)

@Composable
fun WeatherIconImage(weatherIconUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(model = weatherIconUrl),
        contentDescription = "Weather Icon",
        modifier = Modifier
            .size(AppTheme.IconSize.weatherIconSize)
            .padding(AppTheme.spacing.sm)
    )
}
