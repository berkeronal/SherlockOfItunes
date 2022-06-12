package com.berker.sherlockofitunes.presentation.contentlist.uistate

import com.berker.sherlockofitunes.core.BaseUiState
import com.berker.sherlockofitunes.domain.model.Content

data class ContentItemUiState(
    private val content: Content
) : BaseUiState() {
    fun getArtistName() = content.artistName
}
