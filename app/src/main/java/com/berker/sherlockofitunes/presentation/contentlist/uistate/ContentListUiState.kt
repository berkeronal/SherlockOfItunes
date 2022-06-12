package com.berker.sherlockofitunes.presentation.contentlist.uistate

import androidx.paging.PagingData
import com.berker.sherlockofitunes.core.BaseUiState
import com.berker.sherlockofitunes.domain.model.ContentType
import kotlinx.coroutines.flow.Flow

data class ContentListUiState(
    val term: String,
    val contentType: ContentType,
    val contents: Flow<PagingData<ContentItemUiState>>
) : BaseUiState()