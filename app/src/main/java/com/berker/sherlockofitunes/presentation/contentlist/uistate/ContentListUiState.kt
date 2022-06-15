package com.berker.sherlockofitunes.presentation.contentlist.uistate

import androidx.annotation.StringRes
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.berker.sherlockofitunes.core.BaseUiState
import com.berker.sherlockofitunes.domain.model.ContentType
import com.berker.sherlockofitunes.presentation.contentlist.LoadStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ContentListUiState(
    val term: String = "avatar",
    val contentType: ContentType = ContentType.Music,
    val contents: Flow<PagingData<ContentItemUiState>> = emptyFlow(),
    val loadState: LoadState = LoadState.Loading,
    @StringRes val termError: Int? = null
) : BaseUiState() {

    fun getErrorState(): Boolean = termError != null

    fun getProgressBarVisibility() = getVisibility(isVisible = loadState == LoadState.Loading)

    fun getContentTypeString() = contentType.value
}