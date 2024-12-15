package com.weatherly.data.mapper

import com.weatherly.core.dto.WeatherResponseDto
import com.weatherly.domain.model.WeatherInfo

fun WeatherResponseDto.toDomain() = WeatherInfo(
    cityName = location.name,
    temperature = current.temp,
    conditionText = current.condition.text,
    conditionIconUrl = current.condition.icon.toHighResolutionIconUrl(),
    humidity = current.humidity,
    uvIndex = current.uv,
    feelsLike = current.feelsLike
)

private fun String.toHighResolutionIconUrl(): String {
    return "https:${this.replace("64x64", "128x128")}"
}
