package com.berker.sherlockofitunes.domain.repository

import androidx.paging.PagingData
import com.berker.sherlockofitunes.domain.model.Content
import kotlinx.coroutines.flow.Flow

interface ITunesContentRepository {

    fun getContentsByTerm(
        term: String,
        mediaType: String
    ): Flow<PagingData<Content>>
}