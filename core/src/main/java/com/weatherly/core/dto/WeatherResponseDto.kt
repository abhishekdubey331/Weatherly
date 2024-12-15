package com.weatherly.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val location: LocationDto,
    val current: CurrentDto
)

@Serializable
data class LocationDto(
    val name: String
)

@Serializable
data class CurrentDto(
    @SerialName("temp_c") val temp: Double,
    val condition: ConditionDto,
    val humidity: Int,
    val uv: Double,
    @SerialName("feelslike_c") val feelsLike: Double
)

@Serializable
data class ConditionDto(
    val text: String,
    val icon: String
)
