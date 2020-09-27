package com.weather.synzip.presentation.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.weather.synzip.domain.Failure
import com.weather.synzip.domain.Success
import com.weather.synzip.domain.place.GetPlacesUseCase
import com.weather.synzip.domain.place.Place
import com.weather.synzip.domain.place.StorePlaceUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val storePlaceUseCase: StorePlaceUseCase
) :
    ViewModel() {

    @OptIn(InternalCoroutinesApi::class)
    val viewState = getPlacesUseCase()
        .map { result ->
            when (result) {
                is Success -> MainViewState(result.data.map { it.name })
                is Failure -> MainViewState(error = result.exception)
            }
        }
        .asLiveData(viewModelScope.coroutineContext)

    fun storePlace(place: Place) = viewModelScope.launch {
        storePlaceUseCase(place)
    }
}