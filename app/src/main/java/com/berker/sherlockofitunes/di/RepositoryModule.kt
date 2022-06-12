package com.berker.sherlockofitunes.di

import com.berker.sherlockofitunes.data.remote.ITunesContentApi
import com.berker.sherlockofitunes.data.repository.ITunesContentRepositoryImpl
import com.berker.sherlockofitunes.domain.repository.ITunesContentRepository
import com.berker.sherlockofitunes.mapper.DomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideITunesContentRepository(
        api: ITunesContentApi,
        domainMapper: DomainMapper
    ): ITunesContentRepository = ITunesContentRepositoryImpl(api = api, domainMapper = domainMapper)

}