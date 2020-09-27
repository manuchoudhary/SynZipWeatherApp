package com.weather.synzip.data.places

import android.content.Context
import com.weather.synzip.data.database.PlaceDao
import com.weather.synzip.data.database.entites.fromPlace
import com.weather.synzip.data.database.entites.toPlace
import com.weather.synzip.domain.Success
import com.weather.synzip.domain.Result
import com.weather.synzip.domain.place.PlacesRepository
import com.weather.synzip.domain.place.Place as PlaceModel
import com.weather.synzip.presentation.core.loadJsonFromAsset
import com.weather.synzip.presentation.core.toObject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

const val CITY_JSON = "city-list.json"

@Singleton
class PlacesDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val placeDao: PlaceDao
) : PlacesRepository {

    override fun observePlaces(): Flow<Result<List<PlaceModel>>> {
        return placeDao.loadAll()
            .map { placeEntities -> Success(placeEntities.map { it.toPlace() }) }
    }

    override suspend fun storePlace(place: PlaceModel) {
        val placeEntity = fromPlace(place)
        placeDao.insert(placeEntity)
    }

    override fun searchPlace(placeName: Flow<String>): Flow<Result<PlaceModel>> {
        val places = mutableListOf<Place>()
        // TODO: Improve this. Change to map instead of list
        return placeName
            .onStart {
                val json = context.loadJsonFromAsset(CITY_JSON)
                val parsedPlaces = json?.toObject<Place>()
                if (parsedPlaces != null) {
                    places.addAll(parsedPlaces)
                }
            }
            .flatMapLatest<String, Result<PlaceModel>> {
                val foundPlace: Place? = places.find { place -> place.name == it }
                flowOf(foundPlace).filter { it != null }.map { Success(PlaceModel(it!!.name, it.id, it.country)) }
            }
    }
}