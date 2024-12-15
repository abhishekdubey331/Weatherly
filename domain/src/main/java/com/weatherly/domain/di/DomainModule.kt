package com.weatherly.domain.di

import com.weatherly.domain.repository.WeatherRepository
import com.weatherly.domain.usecase.CacheWeatherDataUseCase
import com.weatherly.domain.usecase.CacheWeatherDataUseCaseImpl
import com.weatherly.domain.usecase.GetWeatherUseCase
import com.weatherly.domain.usecase.GetWeatherUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetWeatherUseCase(
        weatherRepository: WeatherRepository
    ): GetWeatherUseCase {
        return GetWeatherUseCaseImpl(weatherRepository)
    }

    @Provides
    fun provideSaveWeatherDataUseCase(
        weatherRepository: WeatherRepository
    ): CacheWeatherDataUseCase {
        return CacheWeatherDataUseCaseImpl(weatherRepository)
    }
}
