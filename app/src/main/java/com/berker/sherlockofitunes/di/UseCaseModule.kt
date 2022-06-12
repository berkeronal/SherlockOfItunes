package com.berker.sherlockofitunes.di

import com.berker.sherlockofitunes.domain.repository.ITunesContentRepository
import com.berker.sherlockofitunes.domain.usecase.content.ContentUseCases
import com.berker.sherlockofitunes.domain.usecase.content.GetContentWithPaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideContentUseCases(repository: ITunesContentRepository): ContentUseCases =
        ContentUseCases(
            getContentWithPaging = GetContentWithPaging(repository)
        )
}