package com.weather.synzip.domain.place

import com.weather.synzip.domain.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchPlacesUseCase @Inject constructor(private val placesRepository: PlacesRepository) {

    operator fun invoke(placeName: Flow<String>): Flow<Result<Place>> {
        return placesRepository.searchPlace(placeName)
    }
}
