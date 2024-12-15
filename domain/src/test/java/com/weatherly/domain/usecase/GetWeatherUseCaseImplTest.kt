package com.weatherly.domain.usecase

import com.weatherly.domain.model.WeatherInfo
import com.weatherly.domain.repository.WeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetWeatherUseCaseImplTest {

    private val weatherRepository: WeatherRepository = mock()

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setUp() {
        getWeatherUseCase = GetWeatherUseCaseImpl(weatherRepository)
    }

    @Test
    fun `Given city name, When invoke is called, Then fetchWeather is called`() = runTest {
        // Given
        val city = "New York"
        val weatherInfo = WeatherInfo(
            cityName = city,
            temperature = 25.0,
            conditionText = "Sunny",
            conditionIconUrl = "icon_url",
            humidity = 70,
            uvIndex = 4.0,
            feelsLike = 26.0
        )
        whenever(weatherRepository.fetchWeather(city)).thenReturn(weatherInfo)

        // When
        val result = getWeatherUseCase.invoke(city)

        // Then
        verify(weatherRepository).fetchWeather(city)
        assertEquals(weatherInfo, result)
    }

    @Test
    fun `When invoke with no arguments is called, Then getCachedWeather is called`() = runTest {
        // Given
        val cachedWeather = WeatherInfo(
            cityName = "Paris",
            temperature = 20.0,
            conditionText = "Cloudy",
            conditionIconUrl = "icon_url",
            humidity = 60,
            uvIndex = 2.5,
            feelsLike = 21.0
        )
        whenever(weatherRepository.getCachedWeather()).thenReturn(cachedWeather)

        // When
        val result = getWeatherUseCase.invoke()

        // Then
        verify(weatherRepository).getCachedWeather()
        assertEquals(cachedWeather, result)
    }
}
