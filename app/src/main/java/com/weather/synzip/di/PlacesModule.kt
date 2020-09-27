package com.weather.synzip.di

import android.content.Context
import com.weather.synzip.data.database.PlaceDao
import com.weather.synzip.data.places.PlacesDataStore
import com.weather.synzip.domain.place.PlacesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object PlacesModule {

    @Singleton
    @Provides
    fun providePlacesRemoteDataStore(
        @ApplicationContext context: Context,
        placeDao: PlaceDao
    ): PlacesRepository =
        PlacesDataStore(context, placeDao)
}