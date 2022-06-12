package com.berker.sherlockofitunes.domain.usecase.content

import androidx.paging.PagingData
import com.berker.sherlockofitunes.data.remote.dto.ContentDTO
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.repository.ITunesContentRepository
import kotlinx.coroutines.flow.Flow

class GetContentWithPaging(
    private val repository: ITunesContentRepository
) {

    operator fun invoke(): Flow<PagingData<Content>> = repository.getContentsByTerm(
        "berk",
        "music"
    )
}