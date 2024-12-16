package com.weatherly.data.repository

import com.weatherly.core.utils.DispatcherProvider
import com.weatherly.data.local.WeatherLocalDataSource
import com.weatherly.data.mapper.toDomain
import com.weatherly.data.remote.WeatherRemoteDataSource
import com.weatherly.domain.model.WeatherInfo
import com.weatherly.domain.repository.WeatherRepository
import javax.inject.Inject
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
    private val dispatcherProvider: DispatcherProvider
) : WeatherRepository {

    override suspend fun fetchWeather(city: String) = withContext(dispatcherProvider.io) {
        remoteDataSource.getCurrentWeather(city).toDomain()
    }

    override suspend fun getCachedWeather() = withContext(dispatcherProvider.io) {
        localDataSource.getWeatherData()
    }

    override suspend fun cacheWeatherData(weatherInfo: WeatherInfo) =
        withContext(dispatcherProvider.io) {
            localDataSource.saveWeatherData(weatherInfo)
        }
}
