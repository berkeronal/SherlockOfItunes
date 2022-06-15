package com.berker.sherlockofitunes.common

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.berker.sherlockofitunes.R
import com.berker.sherlockofitunes.presentation.widget.FilterButtonGroup
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

@SuppressLint("SimpleDateFormat")
@BindingAdapter("releaseDate")
fun TextView.setReleaseDate(value: String) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val convertedDate: Date?
    try {
        convertedDate = dateFormat.parse(value)
        val sdfNewFormat = SimpleDateFormat("MMM dd yyyy")
        val finalDateString = convertedDate?.let { sdfNewFormat.format(it) }
        text = finalDateString

    } catch (e: ParseException) {
        text = context.resources.getText(R.string.error_date)
    }
}

@BindingAdapter("setPrice")
fun TextView.setPrice(value: Double){
    text = context.resources.getString(R.string.text_with_dollar_symbol, 2.5)
}
