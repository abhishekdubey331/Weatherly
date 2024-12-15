package com.weatherly.domain.usecase

import com.weatherly.domain.model.WeatherInfo
import com.weatherly.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GetWeatherUseCase {

    override suspend fun invoke(city: String): WeatherInfo {
        return weatherRepository.fetchWeather(city)
    }

    override suspend fun invoke(): WeatherInfo? {
        return weatherRepository.getCachedWeather()
    }
}
