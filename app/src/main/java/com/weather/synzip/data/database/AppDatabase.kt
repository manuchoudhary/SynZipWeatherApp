package com.weather.synzip.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.synzip.data.database.entites.PlaceEntity
import com.weather.synzip.data.database.entites.WeatherEntity

@Database(entities = [PlaceEntity::class, WeatherEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun weatherDao(): WeatherDao
}