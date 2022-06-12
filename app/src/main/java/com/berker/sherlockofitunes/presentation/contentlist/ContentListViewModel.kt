package com.berker.sherlockofitunes.presentation.contentlist

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berker.sherlockofitunes.core.BaseViewModel
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.usecase.content.ContentUseCases
import com.berker.sherlockofitunes.mapper.DomainMapper
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentItemUiState
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ContentListViewModel @Inject constructor(
    private val contentUseCases: ContentUseCases,
    private val domainMapper: DomainMapper

) : BaseViewModel() {
    private val _contentUiState = MutableStateFlow(ContentListUiState())
    val contentUiState get() = _contentUiState.asStateFlow()

    fun getContent() {
        contentUseCases.getContentWithPaging().map {
            it.map {
                domainMapper.domainContentToDomainContentItemUiState(it)
            }
        }.cachedIn(viewModelScope).also {
            setContent(it)
        }
    }

    fun setLoadState(loadState: LoadState) {
        _contentUiState.update { oldState ->
            oldState.copy(
                loadState = loadState
            )
        }
    }

    private fun setContent(content: Flow<PagingData<ContentItemUiState>>) {
        _contentUiState.update { oldState ->
            oldState.copy(contents = content)
        }
    }
}