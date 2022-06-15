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
            artworkUrl256 = artworkUrl100.replaceIfNull().replace("100x100", "256x256"),
            artworkUrl100 = artworkUrl100.replaceIfNull(),
            artworkUrl60 = artworkUrl60.replaceIfNull(),
            artworkUrl30 = artworkUrl30.replaceIfNull(),
            trackCount = trackCount.replaceIfNull(),
            trackExplicitness = trackExplicitness.replaceIfNull(),
            trackId = trackId.replaceIfNull(),
            collectionName = collectionName.replaceIfNull(),
            collectionPrice = collectionPrice.replaceIfNull(),
            releaseDate = releaseDate.replaceIfNull(),
            longDescription = longDescription.replaceIfNull(),
            trackViewUrl = trackViewUrl.replaceIfNull(),
            previewUrl  = previewUrl.replaceIfNull(),
            collectionViewUrl = collectionViewUrl.replaceIfNull(),
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
        ContentItemUiState(
            artistId = artistId,
            artistName = artistName,
            artworkUrl256 = artworkUrl256,
            artworkUrl100 = artworkUrl100,
            artworkUrl30 = artworkUrl30,
            artworkUrl60 = artworkUrl60,
            trackCount = trackCount,
            trackExplicitness = trackExplicitness,
            trackId = trackId,
            collectionName = collectionName,
            collectionPrice = collectionPrice,
            releaseDate = releaseDate,
            trackViewUrl = trackViewUrl.replaceIfNull(),
            previewUrl  = previewUrl.replaceIfNull(),
            collectionViewUrl = collectionViewUrl.replaceIfNull(),
        )
    }
}