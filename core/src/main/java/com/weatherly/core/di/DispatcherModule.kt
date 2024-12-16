package com.weatherly.core.di

import com.weatherly.core.utils.CoroutineDispatcherProvider
import com.weatherly.core.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = CoroutineDispatcherProvider()
}
