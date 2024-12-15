package com.weatherly.app.ui.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.weatherly.app.ui.theme.AppTheme

@Composable
fun LocalWeatherInfoCard(
    cityName: String,
    temperature: String,
    weatherIconUrl: String,
    humidity: String,
    uvIndex: String,
    feelsLike: String
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = AppTheme.spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(AppTheme.spacing.lg))
        WeatherIcon(weatherIconUrl)
        CityNameWithIcon(cityName)
        TemperatureDisplay(temperature)
        Spacer(modifier = Modifier.height(AppTheme.spacing.xl))
        WeatherDetailsCard(humidity, uvIndex, feelsLike)
    }
}

@Composable
private fun WeatherIcon(weatherIconUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(weatherIconUrl),
        modifier = Modifier.size(AppTheme.IconSize.weatherIconSize),
        contentDescription = "Weather Icon"
    )
}

@Composable
private fun CityNameWithIcon(cityName: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = cityName, style = cityTitle
        )
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location Icon",
            tint = Color.Black,
            modifier = Modifier.size(AppTheme.spacing.md)
        )
    }
}

@Composable
private fun TemperatureDisplay(temperature: String) {
    Spacer(modifier = Modifier.height(AppTheme.spacing.sm))
    Text(
        text = "$temperature°", style = temperatureDisplay
    )
}

@Composable
private fun WeatherDetailsCard(humidity: String, uvIndex: String, feelsLike: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.md),
        shape = RoundedCornerShape(AppTheme.spacing.lg),
        elevation = AppTheme.spacing.xs,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        Row(
            modifier = Modifier.padding(AppTheme.spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailItem(label = "Humidity", value = "$humidity%")
            WeatherDetailItem(label = "UV", value = uvIndex)
            WeatherDetailItem(label = "Feels Like", value = feelsLike)
        }
    }
}

@Composable
private fun WeatherDetailItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label, fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.xs))
        Text(
            text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9A9A9A)
        )
    }
}

private val cityTitle = TextStyle(
    fontFamily = AppTheme.fontFamily,
    fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black
)

private val temperatureDisplay = TextStyle(
    fontFamily = AppTheme.fontFamily,
    fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.Black
)

@Preview(showBackground = true)
@Composable
fun WeatherInfoCardPreview() {
    LocalWeatherInfoCard(
        cityName = "Hyderabad",
        temperature = "31",
        weatherIconUrl = "https://cdn.weatherapi.com/weather/64x64/day/113.png",
        humidity = "20%",
        uvIndex = "4",
        feelsLike = "38°"
    )
}
