package com.berker.sherlockofitunes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.berker.sherlockofitunes.data.datasource.LocalPagingSource
import com.berker.sherlockofitunes.data.remote.ITunesContentApi
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.repository.ITunesContentRepository
import com.berker.sherlockofitunes.mapper.DomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ITunesContentRepositoryImpl @Inject constructor(
    private val api: ITunesContentApi,
    private val domainMapper: DomainMapper
) : ITunesContentRepository {
    override fun getContentsByTerm(
        term: String,
        mediaType: String
    ): Flow<PagingData<Content>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            LocalPagingSource { offset ->
                makeApiCall(offset,term, mediaType)
            }
        }
    ).flow.map {
        it.map {content->
            domainMapper.dataContentToDomainContent(content)
        }
    }

    private suspend fun makeApiCall(offset:Int,term: String,mediaType: String)=
        api.getContentsBySearch(
            offset = offset,
            term = term,
            mediaType = mediaType,
            limit = 20
        ).results

    companion object {
        const val PAGE_SIZE = 20
    }
}