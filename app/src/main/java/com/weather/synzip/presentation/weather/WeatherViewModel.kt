package com.weather.synzip.presentation.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.synzip.TemperatureUnit
import com.weather.synzip.core.Event
import com.weather.synzip.domain.Failure
import com.weather.synzip.domain.Success
import com.weather.synzip.domain.weather.StoreWeatherUseCase
import com.weather.synzip.domain.weather.Weather
import com.weather.synzip.domain.weather.WeatherUseCase
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject constructor(
    val weatherUseCase: WeatherUseCase,
    val storeWeatherUseCase: StoreWeatherUseCase,
    private val weatherMapper: WeatherMapper
) : ViewModel() {

    private val _viewState = MutableLiveData<WeatherViewState>()
    val viewState: LiveData<WeatherViewState>
        get() = _viewState

    lateinit var weather: Weather

    fun showWeather(placeName: String) = viewModelScope.launch {
        showLoading()
        when (val weatherResult = weatherUseCase(placeName)) {
            is Success -> {
                val tempUnit = getTemperatureUnit()
                val weatherData = weatherMapper.map(placeName, weatherResult.data, tempUnit)
                weather = weatherResult.data
                emitUiState(showSuccess = weatherData)
            }
            is Failure -> {
                emitUiState(showError = weatherResult.exception)
            }
        }
    }

    fun storeWeather(data: WeatherData?) = viewModelScope.launch {
        storeWeatherUseCase(weather, data?.place!!)
    }


    private fun getTemperatureUnit(): TemperatureUnit {
        return TemperatureUnit.CELSIUS
    }

    private fun showLoading() {
        emitUiState(showProgress = Event(true))
    }

    private fun emitUiState(
        showProgress: Event<Boolean> = Event(false),
        showError: Exception? = null,
        showSuccess: WeatherData? = null
    ) {
        val viewState = WeatherViewState(
            showProgress,
            showError,
            showSuccess
        )
        _viewState.value = viewState
    }
}