package com.weather.synzip.data.database.entites

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.weather.synzip.domain.weather.*

@Entity(
    tableName = "weather", indices = [Index(
        value = ["place", "description", "type", "temperature",
            "maxTemperature", "minTemperature", "pressure", "humidity", "windSpeed", "windDirection"],
        unique = true
    )]
)
data class WeatherEntity(
    val place: String,
    val description: String?,
    val type: String,
    val temperature: Double,
    val maxTemperature: Double,
    val minTemperature: Double,
    val pressure: Double,
    val humidity: Double,
    val windSpeed: Double,
    val windDirection: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun fromWeather(weather: Weather, place: String) : WeatherEntity {
    return WeatherEntity(place, weather.description,
        weather.type.name, weather.temperature.value, weather.maxTemperature.value,
        weather.minTemperature.value, weather.pressure.value, weather.humidity.value,
        weather.wind.speed, weather.wind.direction)
}
fun WeatherEntity.toWeather() = Weather(this.description, WeatherType.valueOf(this.type),
    Temperature(this.temperature), Temperature(this.maxTemperature),
    Temperature(this.minTemperature), Pressure(this.pressure), Humidity(this.humidity),
    Wind(this.windSpeed, this.windDirection)
)