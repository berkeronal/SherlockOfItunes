package com.berker.sherlockofitunes.common

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

@BindingAdapter("releaseDate")
fun TextView.setReleaseDate(value: String) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    var convertedDate = Date()
    try {
        convertedDate = dateFormat.parse(value)
        val sdfnewformat = SimpleDateFormat("MMM dd yyyy")
        val finalDateString = sdfnewformat.format(convertedDate)
        text = finalDateString

    } catch (e: ParseException) {
        text = "Error While Formatting Date"
    }
}

@BindingAdapter("setPrice")
fun TextView.setPrice(value: String){
    text = "$ $value"
}
