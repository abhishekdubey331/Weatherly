package com.weatherly.domain.usecase

import com.weatherly.domain.model.WeatherInfo
import com.weatherly.domain.repository.WeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

class CacheWeatherDataUseCaseImplTest {

    private val weatherRepository: WeatherRepository = mock()

    private lateinit var saveWeatherDataUseCase: CacheWeatherDataUseCase

    @Before
    fun setUp() {
        saveWeatherDataUseCase = CacheWeatherDataUseCaseImpl(weatherRepository)
    }

    @Test
    fun `Given weatherInfo, When invoke is called, Then cacheWeatherData is called`() = runTest {
        // Given
        val weatherInfo = WeatherInfo(
            cityName = "Tokyo",
            temperature = 30.0,
            conditionText = "Sunny",
            conditionIconUrl = "icon_url",
            humidity = 80,
            uvIndex = 7.0,
            feelsLike = 32.0
        )

        // When
        saveWeatherDataUseCase.invoke(weatherInfo)

        // Then
        verify(weatherRepository).cacheWeatherData(weatherInfo)
    }
}
