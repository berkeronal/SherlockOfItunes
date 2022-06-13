package com.berker.sherlockofitunes.presentation.contentlist

import com.berker.sherlockofitunes.domain.model.ContentType

sealed class ContentListUiEvent {
    class OnContentTypeChanged(val value: ContentType) : ContentListUiEvent()
    class OnTermChanged(val term: String) : ContentListUiEvent()

}
