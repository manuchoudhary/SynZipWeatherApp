package com.weather.synzip.presentation.weather

import com.weather.synzip.TemperatureUnit
import com.weather.synzip.domain.weather.Humidity
import com.weather.synzip.domain.weather.Pressure

data class WeatherData(
    val place: String,
    val tempData: TemperatureData,
    val iconRes: Int,
    val maxTemperature: TemperatureData,
    val minTemperature: TemperatureData,
    val name: String,
    val humidity: Humidity,
    val pressure: Pressure
)

data class TemperatureData(val degrees: Double, val tempUnit: TemperatureUnit)