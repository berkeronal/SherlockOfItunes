package com.berker.sherlockofitunes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.berker.sherlockofitunes.data.datasource.LocalPagingSource
import com.berker.sherlockofitunes.data.remote.ITunesContentApi
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.repository.ITunesContentRepository
import com.berker.sherlockofitunes.mapper.DomainMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ITunesContentRepositoryImpl @Inject constructor(
    private val api: ITunesContentApi,
    private val domainMapper: DomainMapper
) : ITunesContentRepository {
    override fun getContentsByTerm(
        term: String,
        mediaType: String
    ): Flow<PagingData<Content>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                LocalPagingSource { offset ->
                    api.getContentsBySearch(
                        offset = offset,
                        term = term,
                        mediaType = mediaType,
                        limit = 20
                    ).results.map {
                        domainMapper.dataContentToDomainContent(it)
                    }
                }
            }

        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}