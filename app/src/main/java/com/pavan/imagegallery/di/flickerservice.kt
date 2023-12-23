package com.pavan.imagegallery.di

import com.pavan.imagegallery.server.flickerapiservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object flickerservice {

    private val BASE_URL = "https://api.flickr.com/services/"

    @Provides
    @Singleton
    fun provideFlickerApi(): flickerapiservice {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(flickerapiservice::class.java)
    }
}