package com.berker.sherlockofitunes.di

import com.berker.sherlockofitunes.mapper.DomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    @Singleton
    fun provideDomainMapper() = DomainMapper()
}