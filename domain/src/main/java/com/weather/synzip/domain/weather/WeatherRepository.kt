package com.weather.synzip.domain.weather

import com.weather.synzip.domain.Result

interface WeatherRepository {

    suspend fun fetchWeatherData(placeName: String): Result<Weather>

    suspend fun storeWeatherData(weather: Weather, place: String)
}
