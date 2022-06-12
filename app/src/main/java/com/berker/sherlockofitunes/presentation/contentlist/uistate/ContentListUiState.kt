package com.berker.sherlockofitunes.presentation.contentlist.uistate

import androidx.paging.LoadState
import androidx.paging.PagingData
import com.berker.sherlockofitunes.core.BaseUiState
import com.berker.sherlockofitunes.domain.model.ContentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ContentListUiState(
    val term: String = "",
    val contentType: ContentType = ContentType.Movie,
    val contents: Flow<PagingData<ContentItemUiState>> = emptyFlow(),
    val loadState: LoadState = LoadState.Loading,
) : BaseUiState()