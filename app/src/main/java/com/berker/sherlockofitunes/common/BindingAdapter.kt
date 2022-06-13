package com.berker.sherlockofitunes.common

import androidx.databinding.BindingAdapter
import com.berker.sherlockofitunes.presentation.widget.FilterButtonGroup

@BindingAdapter("checked")
fun FilterButtonGroup.setChecked(value: String) {
    setChecked(value)
}
