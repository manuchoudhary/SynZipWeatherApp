package com.weather.synzip.domain.place

import com.weather.synzip.domain.place.Place
import com.weather.synzip.domain.place.PlacesRepository
import javax.inject.Inject

class StorePlaceUseCase @Inject constructor(private val placesRepository: PlacesRepository) {

    suspend operator fun invoke(place: Place) {
        return placesRepository.storePlace(place)
    }
}
