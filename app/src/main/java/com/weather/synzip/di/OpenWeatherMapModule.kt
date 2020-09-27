package com.weather.synzip.di

import com.weather.synzip.data.database.WeatherDao
import com.weather.synzip.data.openweathermap.OpenWeatherMapApi
import com.weather.synzip.data.weather.WeatherDataStore
import com.weather.synzip.domain.weather.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.weather.synzip.BuildConfig

const val OPENWEATHERMAP_URL = BuildConfig.OPENWEATHERMAP_URL

@InstallIn(ApplicationComponent::class)
@Module
object OpenWeatherMapModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OPENWEATHERMAP_URL)
            .addConverterFactory(
                MoshiConverterFactory.create()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideOpenWeatherMapApi(retrofit: Retrofit): OpenWeatherMapApi {
        return retrofit.create(OpenWeatherMapApi::class.java)
    }

    @Provides
    fun provideOpenWeatherMapDataStore(openWeatherMapApi: OpenWeatherMapApi, weatherDao: WeatherDao): WeatherRepository {
        return WeatherDataStore(openWeatherMapApi, weatherDao)
    }
}