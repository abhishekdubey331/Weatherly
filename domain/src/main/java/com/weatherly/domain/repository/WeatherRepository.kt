package com.weatherly.domain.repository

import com.weatherly.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun fetchWeather(city: String): WeatherInfo
    suspend fun getCachedWeather(): WeatherInfo?
    suspend fun cacheWeatherData(weatherInfo: WeatherInfo)
}
