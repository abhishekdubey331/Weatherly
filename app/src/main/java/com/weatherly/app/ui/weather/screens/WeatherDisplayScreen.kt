package com.weatherly.app.ui.weather.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weatherly.app.ui.theme.AppTheme
import com.weatherly.app.ui.viewstate.WeatherUiState
import com.weatherly.app.ui.weather.components.CitySearchBar
import com.weatherly.app.ui.weather.components.EmptyStateScreen
import com.weatherly.app.ui.weather.components.ErrorMessage
import com.weatherly.app.ui.weather.components.LoadingState
import com.weatherly.app.ui.weather.components.LocalWeatherInfoCard
import com.weatherly.app.ui.weather.components.WeatherCard
import com.weatherly.app.ui.weather.viewmodel.WeatherViewModel
import com.weatherly.core.R
import com.weatherly.domain.model.WeatherInfo

@Composable
fun WeatherDisplayScreen(modifier: Modifier = Modifier) {
    val viewModel: WeatherViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CitySearchBar(query = searchQuery, onQueryChange = {
            viewModel.onSearchQueryChanged(it)
        })
        Spacer(modifier = Modifier.height(AppTheme.spacing.md))
        WeatherContent(uiState, viewModel)
    }
}

@Composable
fun WeatherContent(uiState: WeatherUiState, viewModel: WeatherViewModel) {
    when (uiState) {
        is WeatherUiState.Loading -> {
            LoadingState()
        }

        is WeatherUiState.Success -> {
            WeatherCardContent(uiState.weatherInfo) { weatherInfo ->
                viewModel.cacheWeatherData(weatherInfo)
            }
        }

        is WeatherUiState.CachedData -> {
            LocalWeatherCardContent(uiState.cachedWeatherInfo)
        }

        is WeatherUiState.Error -> {
            val errorText = uiState.message ?: uiState.resourceId?.let { stringResource(id = it) }
                ?: stringResource(id = R.string.error_generic)

            ErrorMessage(errorText)
        }

        is WeatherUiState.Empty -> {
            EmptyStateScreen()
        }
    }
}

@Composable
fun WeatherCardContent(weatherInfo: WeatherInfo?, onClick: (WeatherInfo) -> Unit) {
    weatherInfo ?: return
    WeatherCard(
        cityName = weatherInfo.cityName,
        temperature = weatherInfo.temperature.toString(),
        weatherIconUrl = weatherInfo.conditionIconUrl,
        onWeatherDataClicked = { onClick(weatherInfo) }
    )
}

@Composable
fun LocalWeatherCardContent(cachedInfo: WeatherInfo?) {
    cachedInfo ?: return
    LocalWeatherInfoCard(
        cityName = cachedInfo.cityName,
        temperature = cachedInfo.temperature.toString(),
        weatherIconUrl = cachedInfo.conditionIconUrl,
        humidity = cachedInfo.humidity.toString(),
        uvIndex = cachedInfo.uvIndex.toString(),
        feelsLike = cachedInfo.feelsLike.toString()
    )
}
