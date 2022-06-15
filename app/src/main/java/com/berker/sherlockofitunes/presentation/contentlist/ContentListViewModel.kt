package com.berker.sherlockofitunes.presentation.contentlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.berker.sherlockofitunes.R
import com.berker.sherlockofitunes.core.BaseViewModel
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.model.ContentType
import com.berker.sherlockofitunes.domain.usecase.content.ContentUseCases
import com.berker.sherlockofitunes.mapper.DomainMapper
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentItemUiState
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentListViewModel @Inject constructor(
    private val contentUseCases: ContentUseCases,
    private val domainMapper: DomainMapper

) : BaseViewModel() {
    private val _contentListUiState = MutableStateFlow(ContentListUiState())
    val contentListUiState get() = _contentListUiState.asStateFlow()

    private var _contentList: ArrayList<Content> = arrayListOf()
    val contentList get() = _contentList

    private var onUserInputJob: Job? = null

    private val oldTerm = MutableLiveData<String>()
    private val oldType = MutableLiveData<ContentType>()

    companion object {
        const val INPUT_TIME_OUT = 500L
        const val MIN_SEARCH_SIZE = 3
    }


    fun getContent() {
        _contentListUiState.value.apply {
            if (oldTerm.value == term && oldType.value == contentType) return
        }

        val response = contentUseCases.getContentWithPaging(
            _contentListUiState.value.term,
            _contentListUiState.value.contentType
        ).map {
            it.map { content ->
                _contentList.add(content)
                domainMapper.domainContentToDomainContentItemUiState(content)
            }
        }.cachedIn(viewModelScope)

        oldTerm.postValue(_contentListUiState.value.term)
        oldType.postValue(_contentListUiState.value.contentType)
        setContent(response)
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
            is ContentListUiEvent.OnTermChanged -> setTerm(event.term)
        }
    }

    fun getContentByContentListUiState(state: ContentItemUiState): Content {
        return _contentList.find {
            it.artistName == state.artistName &&
                    it.artistName == state.artistName &&
                    it.trackId == state.trackId &&
                    it.releaseDate == state.releaseDate
        } ?: _contentList.first()
    }

    private fun setTerm(term: String) {
        if (oldTerm.value == term) {
            onUserInputJob?.cancel()
            return
        }

        onUserInputJob?.cancel()
        onUserInputJob = viewModelScope.launch {
            delay(INPUT_TIME_OUT / 2)

            if (term.length < MIN_SEARCH_SIZE) {
                _contentListUiState.update { oldState ->
                    oldState.copy(
                        termError = R.string.error_term,
                        term = term
                    )
                }
                this.cancel()
                return@launch
            }
            delay(INPUT_TIME_OUT / 2)

            _contentListUiState.update { oldState ->
                oldState.copy(
                    term = term,
                    termError = if (_contentListUiState.value.getErrorState()) null else oldState.termError
                )
            }.also {
                getContent()
            }
        }
    }

    private fun setContentType(contentType: ContentType) {
        _contentListUiState.update { oldstate ->
            oldstate.copy(contentType = contentType)
        }.also {
            getContent()
        }
    }

    private fun setContent(content: Flow<PagingData<ContentItemUiState>>) {
        _contentListUiState.update { oldState ->
            oldState.copy(contents = content)
        }
    }
}