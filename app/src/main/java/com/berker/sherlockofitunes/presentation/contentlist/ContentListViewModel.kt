package com.berker.sherlockofitunes.presentation.contentlist

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berker.sherlockofitunes.core.BaseViewModel
import com.berker.sherlockofitunes.domain.model.ContentType
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
    private val _contentListUiState = MutableStateFlow(ContentListUiState())
    val contentListUiState get() = _contentListUiState.asStateFlow()

    fun getContent() {
        contentUseCases.getContentWithPaging().map {
            it.map { content ->
                domainMapper.domainContentToDomainContentItemUiState(content)
            }
        }.cachedIn(viewModelScope)
            .also {
                setContent(it)
            }
    }

    fun setLoadState(loadState: LoadState) {
        _contentListUiState.update { oldState ->
            oldState.copy(
                loadState = loadState
            )
        }
    }

    fun onEvent(event: ContentListUiEvent) {
        when (event) {
            is ContentListUiEvent.OnContentTypeChanged -> setContentType(event.value)
            is ContentListUiEvent.OnTermChanged -> TODO()
        }
    }

    private fun setContentType(contentType: ContentType) {
        _contentListUiState.update { oldstate ->
            oldstate.copy(contentType = contentType)
        }
    }

    private fun setContent(content: Flow<PagingData<ContentItemUiState>>) {
        _contentListUiState.update { oldState ->
            oldState.copy(contents = content)
        }
    }
}