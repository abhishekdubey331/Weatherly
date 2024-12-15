package com.weatherly.data.repository

import com.weatherly.core.dto.ConditionDto
import com.weatherly.core.dto.CurrentDto
import com.weatherly.core.dto.LocationDto
import com.weatherly.core.dto.WeatherResponseDto
import com.weatherly.data.local.WeatherLocalDataSource
import com.weatherly.data.mapper.toDomain
import com.weatherly.data.remote.WeatherRemoteDataSource
import com.weatherly.domain.repository.WeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class WeatherRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: WeatherRemoteDataSource

    @Mock
    private lateinit var localDataSource: WeatherLocalDataSource

    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = WeatherRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `Given remote data source returns weather response, When fetchWeather is called, Then return WeatherInfo`() =
        runTest {
            // Given
            val city = "London"
            whenever(remoteDataSource.getCurrentWeather(city)).thenReturn(weatherResponseDto)

            // When
            val result = repository.fetchWeather(city)

            // Then
            verify(remoteDataSource).getCurrentWeather(city)
            assertEquals(weatherResponseDto.toDomain(), result)
        }

    @Test
    fun `Given cached weather data exists, When getCachedWeather is called, Then return cached WeatherInfo`() =
        runTest {
            // Given
            val cachedWeather = weatherResponseDto.toDomain()
            whenever(localDataSource.getWeatherData()).thenReturn(cachedWeather)

            // When
            val result = repository.getCachedWeather()

            // Then
            verify(localDataSource).getWeatherData()
            assertEquals(cachedWeather, result)
        }

    @Test
    fun `Given no cached weather data exists, When getCachedWeather is called, Then return null`() =
        runTest {
            // Given
            whenever(localDataSource.getWeatherData()).thenReturn(null)

            // When
            val result = repository.getCachedWeather()

            // Then
            verify(localDataSource).getWeatherData()
            assertEquals(null, result)
        }

    @Test
    fun `cacheWeatherData saves WeatherInfo to local data source`() = runTest {
        // Given
        val weatherInfo = weatherResponseDto.toDomain()

        // When
        repository.cacheWeatherData(weatherInfo)

        // Then
        verify(localDataSource).saveWeatherData(weatherInfo)
    }

    companion object {
        private val weatherResponseDto = WeatherResponseDto(
            location = LocationDto(
                name = "Bangalore"
            ),
            current = CurrentDto(
                temp = 25.5,
                condition = ConditionDto(
                    text = "Clear Sky",
                    icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
                ),
                humidity = 60,
                uv = 5.5,
                feelsLike = 27.0
            )
        )
    }
}
