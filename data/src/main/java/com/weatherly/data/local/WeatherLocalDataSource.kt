package com.weatherly.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.weatherly.domain.model.WeatherInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WeatherLocalDataSource @Inject constructor(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore("weather_preferences")
    private var cachedWeatherInfo: WeatherInfo? = null

    suspend fun saveWeatherData(weatherInfo: WeatherInfo) {
        cachedWeatherInfo = weatherInfo
        val weatherJson = Json.encodeToString(weatherInfo)
        context.dataStore.edit { preferences ->
            preferences[WEATHER_INFO_KEY] = weatherJson
        }
    }

    suspend fun getWeatherData(): WeatherInfo? {
        cachedWeatherInfo?.let {
            return it
        }
        val weatherJson = context.dataStore.data
            .map { preferences -> preferences[WEATHER_INFO_KEY] }
            .first()
        return weatherJson?.let {
            Json.decodeFromString<WeatherInfo>(it).also { weatherInfo ->
                cachedWeatherInfo = weatherInfo
            }
        }
    }

    companion object {
        val WEATHER_INFO_KEY = stringPreferencesKey("weather_info")
    }
}
