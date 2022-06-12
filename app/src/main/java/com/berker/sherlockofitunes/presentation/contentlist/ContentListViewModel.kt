package com.berker.sherlockofitunes.presentation.contentlist

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.berker.sherlockofitunes.core.BaseViewModel
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.domain.usecase.content.ContentUseCases
import com.berker.sherlockofitunes.mapper.DomainMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ContentListViewModel @Inject constructor(
    private val contentUseCases: ContentUseCases,
    private val domainMapper: DomainMapper

) : BaseViewModel() {

    private lateinit var _contentFlow: Flow<PagingData<Content>>
    val contentFlow: Flow<PagingData<Content>>
        get() = _contentFlow

    fun getContent(){
        _contentFlow = contentUseCases.getContentWithPaging().map {
            it.map {
                it
            }
        }
    }
}