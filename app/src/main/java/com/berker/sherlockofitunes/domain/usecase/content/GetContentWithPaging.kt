package com.berker.sherlockofitunes.domain.usecase.content

import androidx.paging.PagingData
import com.berker.sherlockofitunes.data.remote.dto.ContentDTO
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.model.ContentType
import com.berker.sherlockofitunes.domain.repository.ITunesContentRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType

class GetContentWithPaging(
    private val repository: ITunesContentRepository
) {

    operator fun invoke(term:String,contentType: ContentType): Flow<PagingData<Content>> = repository.getContentsByTerm(
        term,
       mediaType = contentType.value
    )
}