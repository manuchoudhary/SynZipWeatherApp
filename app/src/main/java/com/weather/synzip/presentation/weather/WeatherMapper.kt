package com.weather.synzip.presentation.weather

import com.weather.synzip.TemperatureUnit
import com.weather.synzip.domain.weather.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun map(placeName: String, weather: Weather, unit: TemperatureUnit): WeatherData {

        val temperature = weather.temperature

        val tempData = when (unit) {
            TemperatureUnit.CELSIUS -> TemperatureData(
                convertToCelsius(temperature.value),
                TemperatureUnit.CELSIUS
            )
        }

        val iconRes = weather.type.iconResource()

        val maxTemp = when (unit) {
            TemperatureUnit.CELSIUS -> TemperatureData(
                convertToCelsius(weather.maxTemperature.value),
                TemperatureUnit.CELSIUS
            )
        }

        val minTemp = when (unit) {
            TemperatureUnit.CELSIUS -> TemperatureData(
                convertToCelsius(weather.minTemperature.value),
                TemperatureUnit.CELSIUS
            )
        }

        return WeatherData(placeName,
            tempData,
            iconRes,
            maxTemp,
            minTemp,
            weather.type.name,
            weather.humidity,
            weather.pressure)
    }
}