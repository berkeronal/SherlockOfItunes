package com.berker.sherlockofitunes.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.berker.sherlockofitunes.R
import com.berker.sherlockofitunes.presentation.widget.FilterButtonGroup
import com.bumptech.glide.Glide

@BindingAdapter("checked")
fun FilterButtonGroup.setChecked(value: String) {
    setChecked(value)
}

@BindingAdapter("imageUrl")
fun ImageView.setContentImage(url: String?) {
    if (url.isNullOrEmpty()) return

    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(this)
}
