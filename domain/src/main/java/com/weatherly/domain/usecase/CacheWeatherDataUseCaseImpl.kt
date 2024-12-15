package com.weatherly.domain.usecase

import com.weatherly.domain.model.WeatherInfo
import com.weatherly.domain.repository.WeatherRepository
import javax.inject.Inject

class CacheWeatherDataUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : CacheWeatherDataUseCase {

    override suspend fun invoke(weatherInfo: WeatherInfo) {
        weatherRepository.cacheWeatherData(weatherInfo)
    }
}
