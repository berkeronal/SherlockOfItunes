package com.berker.sherlockofitunes.presentation.contentlist.uistate

import androidx.paging.LoadState
import com.berker.sherlockofitunes.core.BaseUiState

class ContentListFooterUiState(
    private val loadState: LoadState,
) : BaseUiState() {
    fun getErrorMessage() = (loadState is LoadState.Error).let {
        if (it) (loadState as LoadState.Error).error.message else "Something not cool happened..."
    }

    fun getErrorVisibility() = getVisibility(isVisible = loadState is LoadState.Error)

    fun getLoadingVisibility() = getVisibility(isVisible = loadState is LoadState.Loading)


}