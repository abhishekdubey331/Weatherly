package com.weatherly.data.remote

import com.weatherly.core.dto.ConditionDto
import com.weatherly.core.dto.CurrentDto
import com.weatherly.core.dto.LocationDto
import com.weatherly.core.dto.WeatherResponseDto
import com.weatherly.core.network.api.WeatherApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class WeatherRemoteDataSourceTest {

    private val weatherApiService: WeatherApiService = mock()

    private lateinit var remoteDataSource: WeatherRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = WeatherRemoteDataSource(weatherApiService)
    }

    @Test
    fun `Given API returns success When getCurrentWeather is called Then return WeatherResponseDto`() =
        runTest {
            // Given
            val query = "London"
            val expectedResponse = WeatherResponseDto(
                LocationDto("London"),
                CurrentDto(20.5, ConditionDto("Clear Sky", "icon_url"), 50, 3.5, 21.0)
            )
            whenever(weatherApiService.getCurrentWeather(query)).thenReturn(expectedResponse)

            // When
            val result = remoteDataSource.getCurrentWeather(query)

            // Then
            assertEquals(expectedResponse, result)
        }

    @Test(expected = Exception::class)
    fun `Given API throws exception When getCurrentWeather is called Then throw IOException`() =
        runTest {
            // Given
            val query = "Unknown"
            whenever(weatherApiService.getCurrentWeather(query)).thenThrow(
                RuntimeException("API Error")
            )

            // When
            remoteDataSource.getCurrentWeather(query)
        }
}
