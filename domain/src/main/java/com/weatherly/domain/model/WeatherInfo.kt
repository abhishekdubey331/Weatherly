package com.weatherly.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfo(
    val cityName: String,
    val temperature: Double,
    val conditionText: String,
    val conditionIconUrl: String,
    val humidity: Int,
    val uvIndex: Double,
    val feelsLike: Double
)
