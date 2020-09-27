package com.weather.synzip.presentation.places

import com.weather.synzip.core.Event

data class PlacesViewState(
    val progress: Event<Boolean>,
    val error: Exception?,
    val data: List<Place>?
)