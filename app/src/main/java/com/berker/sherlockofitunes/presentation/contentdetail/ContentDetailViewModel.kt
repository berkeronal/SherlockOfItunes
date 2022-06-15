package com.berker.sherlockofitunes.presentation.contentdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.berker.sherlockofitunes.core.BaseViewModel
import com.berker.sherlockofitunes.domain.model.Content

class ContentDetailViewModel(
    private val state: SavedStateHandle
) : BaseViewModel() {
    private var _content = MutableLiveData<Content>()

    fun setContent(content: Content) {
        _content.postValue(content)
    }
}