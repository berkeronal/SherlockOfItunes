package com.berker.sherlockofitunes.mapper

import com.berker.sherlockofitunes.common.extension.replaceIfNull
import com.berker.sherlockofitunes.data.remote.dto.ContentDTO
import com.berker.sherlockofitunes.data.remote.dto.ContentResultDTO
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.model.ContentResult
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentItemUiState

class DomainMapper {

    fun dataContentToDomainContent(
        dataModel: ContentDTO
    ): Content = with(dataModel) {
        Content(
            artistId = artistId.replaceIfNull(),
            artistName = artistName.replaceIfNull(),
            artworkUrl100 = artworkUrl100.replaceIfNull(),
            trackCount = trackCount.replaceIfNull(),
            trackExplicitness = trackExplicitness.replaceIfNull(),
            trackId = trackId.replaceIfNull(),
            collectionName = collectionName.replaceIfNull(),
            collectionPrice = collectionPrice.replaceIfNull(),
            releaseDate = releaseDate.replaceIfNull()
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

    fun domainContentToDomainContentItemUiState(
        domainModel: Content
    ) = with(domainModel) {
        ContentItemUiState(content = domainModel)
    }
}