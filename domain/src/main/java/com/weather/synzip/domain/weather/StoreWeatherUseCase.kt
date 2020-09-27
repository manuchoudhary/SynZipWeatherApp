package com.weather.synzip.domain.weather

import javax.inject.Inject

class StoreWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(weather: Weather, place: String) {
        weatherRepository.storeWeatherData(weather, place)
    }
}