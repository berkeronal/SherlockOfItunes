package com.berker.sherlockofitunes.core

import android.view.View

open class BaseUiState {
    fun getVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.GONE
}