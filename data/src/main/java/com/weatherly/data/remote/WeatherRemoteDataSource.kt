package com.weatherly.data.remote

import com.weatherly.core.dto.WeatherResponseDto
import com.weatherly.core.network.api.WeatherApiService
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherApiService: WeatherApiService
) {
    suspend fun getCurrentWeather(query: String): WeatherResponseDto {
        return weatherApiService.getCurrentWeather(query = query)
    }
}
