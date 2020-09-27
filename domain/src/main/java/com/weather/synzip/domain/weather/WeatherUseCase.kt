package com.weather.synzip.domain.weather

import com.weather.synzip.domain.Result
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(placeName: String): Result<Weather> {
        return weatherRepository.fetchWeatherData(placeName)
    }
}
