package com.weatherly.data.di

import android.content.Context
import com.weatherly.core.network.api.WeatherApiService
import com.weatherly.data.local.WeatherLocalDataSource
import com.weatherly.data.remote.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: WeatherApiService): WeatherRemoteDataSource {
        return WeatherRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): WeatherLocalDataSource {
        return WeatherLocalDataSource(context)
    }
}
