package com.weatherly.data.repository

import com.weatherly.data.local.WeatherLocalDataSource
import com.weatherly.data.mapper.toDomain
import com.weatherly.data.remote.WeatherRemoteDataSource
import com.weatherly.domain.model.WeatherInfo
import com.weatherly.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {

    override suspend fun fetchWeather(city: String): WeatherInfo {
        return remoteDataSource.getCurrentWeather(city).toDomain()
    }

    override suspend fun getCachedWeather(): WeatherInfo? {
        return localDataSource.getWeatherData()
    }

    override suspend fun cacheWeatherData(weatherInfo: WeatherInfo) {
        localDataSource.saveWeatherData(weatherInfo)
    }
}
