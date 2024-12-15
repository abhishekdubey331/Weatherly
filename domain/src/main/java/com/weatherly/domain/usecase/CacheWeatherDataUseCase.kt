package com.weatherly.domain.usecase

import com.weatherly.domain.model.WeatherInfo

interface CacheWeatherDataUseCase {
    suspend operator fun invoke(weatherInfo: WeatherInfo)
}
