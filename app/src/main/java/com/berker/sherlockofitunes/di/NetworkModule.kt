package com.berker.sherlockofitunes.di

import com.berker.sherlockofitunes.data.remote.ITunesContentApi
import com.berker.sherlockofitunes.data.remote.ITunesContentApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideITunesContentApi(): ITunesContentApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesContentApi::class.java)
    }
}