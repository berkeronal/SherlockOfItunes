package com.berker.sherlockofitunes.presentation.contentlist.uistate

import com.berker.sherlockofitunes.core.BaseUiState
import com.berker.sherlockofitunes.domain.model.Content

data class ContentItemUiState(
    val artistId: Int,
    val artistName: String,
    /**
     * Image resolution was so low so i had to manipulate data
     * by changing at the end of url 100x100 to 256x256
     */
    val artworkUrl256: String,
    val artworkUrl100: String,
    val artworkUrl30: String,
    val artworkUrl60: String,
    val trackCount: Int,
    val trackExplicitness: String,
    val trackId: Int,
    val collectionName: String,
    val collectionPrice: Double,
    val releaseDate: String,
) : BaseUiState() {

}
