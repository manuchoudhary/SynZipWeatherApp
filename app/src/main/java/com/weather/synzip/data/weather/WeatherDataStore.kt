package com.weather.synzip.data.weather

import com.weather.synzip.data.database.WeatherDao
import com.weather.synzip.data.database.entites.fromWeather
import com.weather.synzip.data.database.entites.toWeather
import com.weather.synzip.data.openweathermap.OpenWeatherMapApi
import com.weather.synzip.data.weather.mappers.toWeather
import com.weather.synzip.domain.Success
import com.weather.synzip.domain.Result
import com.weather.synzip.domain.weather.Weather
import com.weather.synzip.domain.weather.WeatherRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import com.weather.synzip.BuildConfig

const val API_KEY = BuildConfig.OPENWEATHERMAP_API_KEY

@Singleton
class WeatherDataStore @Inject constructor(private val api: OpenWeatherMapApi,
                                           private val weatherDao: WeatherDao
) :
    WeatherRepository {
    override suspend fun fetchWeatherData(placeName: String): Result<Weather> {
        return try {
            val weatherDto = api.getWeatherByCityName(
                placeName,
                API_KEY
            )
            Success(weatherDto.toWeather())
        } catch (@Suppress("TooGenericExceptionCaught") exception: Exception) {
            val weatherEntity = weatherDao.loadAll(placeName).map { it.toWeather() }
            Success(weatherEntity.first())
        }
    }

    override suspend fun storeWeatherData(weather: Weather, place: String) {
        val weatherEntity = fromWeather(weather, place)
        weatherDao.insert(weatherEntity)
    }
}