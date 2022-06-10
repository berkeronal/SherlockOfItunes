package com.berker.sherlockofitunes.mapper

import com.berker.sherlockofitunes.data.remote.dto.ContentDTO
import com.berker.sherlockofitunes.data.remote.dto.ContentResultDTO
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.model.ContentResult

class DomainMapper {

    fun dataContentToDomainContent(
        dataModel: ContentDTO
    ): Content = with(dataModel) {
        Content(
            artistId = artistId ?: -1,
            artistName = artistName ?: "",
            artworkUrl100 = artworkUrl100 ?: "",
            trackCount = trackCount ?: -1,
            trackExplicitness = trackExplicitness ?: "",
            trackId = trackId ?: -1,
            collectionName = collectionName ?: "",
            collectionPrice = collectionPrice ?: 0.0,
            releaseDate = releaseDate ?: ""
        )
    }

    fun dataContentResultToDomainContentResult(
        dataModel: ContentResultDTO
    ): ContentResult = with(dataModel) {
        ContentResult(
            resultCount = resultCount,
            results = results.map { dataContentToDomainContent(it) }
        )
    }
}