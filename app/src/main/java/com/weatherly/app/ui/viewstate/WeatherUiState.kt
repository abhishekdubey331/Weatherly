package com.weatherly.app.ui.viewstate

import com.weatherly.domain.model.WeatherInfo

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val weatherInfo: WeatherInfo) : WeatherUiState
    data class CachedData(val cachedWeatherInfo: WeatherInfo) : WeatherUiState
    data class Error(val message: String?, val resourceId: Int?) : WeatherUiState
    data object Empty : WeatherUiState
}
