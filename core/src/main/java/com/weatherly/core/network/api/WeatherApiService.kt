package com.weatherly.core.network.api

import com.weatherly.core.dto.WeatherResponseDto
import com.weatherly.core.utils.ApiConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET(ApiConstants.CURRENT_WEATHER_ENDPOINT)
    suspend fun getCurrentWeather(
        @Query("q") query: String
    ): WeatherResponseDto
}
