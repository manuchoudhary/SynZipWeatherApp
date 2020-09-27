package com.weather.synzip.presentation.weather

import com.weather.synzip.core.Event

data class WeatherViewState(
    val progress: Event<Boolean>,
    val error: Exception?,
    val data: WeatherData?
)