package com.weatherly.domain.usecase

import com.weatherly.domain.model.WeatherInfo

interface GetWeatherUseCase {
    suspend operator fun invoke(city: String): WeatherInfo
    suspend operator fun invoke(): WeatherInfo?
}
