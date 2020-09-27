package com.weather.synzip.data.database.entites

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.weather.synzip.domain.place.Place

@Entity(
    tableName = "places", indices = [Index(
        value = ["name", "externalId"],
        unique = true
    )]
)
data class PlaceEntity(
    var name: String,
    var externalId: String,
    var country: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun fromPlace(place: Place) = PlaceEntity(place.name, place.id, place.country)
fun PlaceEntity.toPlace() = Place(this.name, this.externalId, this.country)