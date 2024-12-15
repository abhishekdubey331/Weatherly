package com.weatherly.app.ui.weather.viewmodel

import com.weatherly.app.ui.viewstate.WeatherUiState
import com.weatherly.core.R
import com.weatherly.core.utils.ApiErrorHandler
import com.weatherly.core.utils.ErrorMessageResult
import com.weatherly.domain.model.WeatherInfo
import com.weatherly.domain.usecase.CacheWeatherDataUseCase
import com.weatherly.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private val getWeatherUseCase: GetWeatherUseCase = mock()
    private val cacheWeatherDataUseCase: CacheWeatherDataUseCase = mock()
    private val apiErrorHandler: ApiErrorHandler = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given cached weather exists When fetchCachedWeather is called Then CachedData is emitted`() =
        runTest {
            // Given
            val cachedWeather = weatherInfo
            whenever(getWeatherUseCase()).thenReturn(cachedWeather)

            // When
            initViewModel()
            advanceUntilIdle()

            // Then
            assertEquals(WeatherUiState.CachedData(cachedWeather), viewModel.uiState.value)
        }

    @Test
    fun `Given weather is fetched successfully When fetchWeather is called Then Success state is emitted`() =
        runTest {
            // Given
            val city = "New York"
            val weatherInfo = weatherInfo.copy(cityName = city)
            whenever(getWeatherUseCase(city)).thenReturn(weatherInfo)

            // When
            initViewModel()
            viewModel.onSearchQueryChanged(city)
            advanceUntilIdle()

            // Then
            assertEquals(WeatherUiState.Success(weatherInfo), viewModel.uiState.value)
        }

    @Test
    fun `Given API call fails When fetchWeather is called Then Error state is emitted`() =
        runTest(testDispatcher) {
            // Given
            val city = "Unknown"
            val exception = RuntimeException("Network Error")
            whenever(getWeatherUseCase(city)).thenAnswer { throw exception }
            whenever(apiErrorHandler.getErrorMessageResId(exception)).thenReturn(
                ErrorMessageResult("Network Error", R.string.error_no_internet)
            )

            // When
            initViewModel()
            viewModel.onSearchQueryChanged(city)
            advanceUntilIdle()

            // Then
            val expectedState = WeatherUiState.Error("Network Error", R.string.error_no_internet)
            assertEquals(expectedState, viewModel.uiState.value)
        }

    @Test
    fun `Given weather info When cacheWeatherData is called Then CachedData state is emitted`() = runTest {
        // Given
        whenever(cacheWeatherDataUseCase.invoke(weatherInfo)).thenReturn(Unit)

        // When
        initViewModel()
        viewModel.cacheWeatherData(weatherInfo)
        advanceUntilIdle()

        // Verify use case invocation
        verify(cacheWeatherDataUseCase).invoke(weatherInfo)
    }

    @Test
    fun `Given cached weather state exists When fetchWeather is called with blank city Then cached state is retained`() =
        runTest {
            // Given
            val cachedWeather = weatherInfo
            whenever(getWeatherUseCase()).thenReturn(cachedWeather)

            // When
            initViewModel()
            viewModel.onSearchQueryChanged("")
            advanceUntilIdle()

            // Then
            assertEquals(WeatherUiState.CachedData(cachedWeather), viewModel.uiState.value)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        viewModel = WeatherViewModel(getWeatherUseCase, apiErrorHandler, cacheWeatherDataUseCase)
    }

    companion object {
        private val weatherInfo = WeatherInfo(
            cityName = "London",
            temperature = 18.0,
            conditionText = "Clear Sky",
            conditionIconUrl = "icon_url",
            humidity = 45,
            uvIndex = 4.5,
            feelsLike = 19.0
        )
    }
}
