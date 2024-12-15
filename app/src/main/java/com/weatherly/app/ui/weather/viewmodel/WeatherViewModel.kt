package com.weatherly.app.ui.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherly.app.ui.viewstate.WeatherUiState
import com.weatherly.core.utils.ApiErrorHandler
import com.weatherly.domain.model.WeatherInfo
import com.weatherly.domain.usecase.CacheWeatherDataUseCase
import com.weatherly.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val apiErrorHandler: ApiErrorHandler,
    private val cacheWeatherDataUseCase: CacheWeatherDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState
    val searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        updateUiState(WeatherUiState.Loading)
        viewModelScope.launch {
            searchQuery.debounce(DEBOUNCE_TIME).distinctUntilChanged()
                .collectLatest { query ->
                    when {
                        query.isBlank() -> fetchCachedWeather()
                        query.length >= MIN_QUERY_LENGTH -> fetchWeather(query)
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    private fun fetchCachedWeather() {
        viewModelScope.launch {
            getWeatherUseCase()?.let { cachedWeather ->
                updateUiState(WeatherUiState.CachedData(cachedWeather))
            } ?: run {
                updateUiState(WeatherUiState.Empty)
            }
        }
    }

    private fun fetchWeather(city: String) {
        updateUiState(WeatherUiState.Loading)
        viewModelScope.launch {
            runCatching { getWeatherUseCase(city) }
                .onSuccess { handleWeatherSuccess(it) }
                .onFailure { handleWeatherError(it) }
        }
    }

    private fun handleWeatherSuccess(weatherInfo: WeatherInfo) {
        updateUiState(WeatherUiState.Success(weatherInfo))
    }

    private fun handleWeatherError(exception: Throwable) {
        val (customMessage, fallbackResId) = apiErrorHandler.getErrorMessageResId(exception)
        updateUiState(WeatherUiState.Error(customMessage, fallbackResId))
    }

    fun cacheWeatherData(weatherInfo: WeatherInfo) {
        viewModelScope.launch {
            cacheWeatherDataUseCase(weatherInfo)
            updateUiState(WeatherUiState.CachedData(weatherInfo))
        }
    }

    private fun updateUiState(newState: WeatherUiState) {
        _uiState.value = newState
    }

    companion object {
        private const val DEBOUNCE_TIME = 300L
        private const val MIN_QUERY_LENGTH = 3
    }
}
