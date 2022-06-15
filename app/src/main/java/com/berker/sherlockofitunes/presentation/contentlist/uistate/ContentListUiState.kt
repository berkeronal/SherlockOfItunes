package com.berker.sherlockofitunes.presentation.contentlist.uistate

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.berker.sherlockofitunes.core.BaseUiState
import com.berker.sherlockofitunes.domain.model.ContentType
import com.berker.sherlockofitunes.presentation.contentlist.LoadStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ContentListUiState(
    val term: String = "",
    val contentType: ContentType = ContentType.Movie,
    val contents: Flow<PagingData<ContentItemUiState>> = emptyFlow(),
    val loadStates: LoadStates = LoadStates.LOADING,
    @StringRes val termError: Int? = null
) : BaseUiState() {

    fun getErrorState(): Boolean = termError != null

    fun getProgressBarVisibility() = getVisibility(isVisible = loadStates == LoadStates.LOADING)

    fun getContentTypeString() = contentType.value
}