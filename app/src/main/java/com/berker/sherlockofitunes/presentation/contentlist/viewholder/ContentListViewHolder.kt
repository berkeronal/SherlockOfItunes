package com.berker.sherlockofitunes.presentation.contentlist.viewholder

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.berker.sherlockofitunes.databinding.ItemRvContentBinding

class ContentListViewHolder(
    private val binding: ItemRvContentBinding,
    private val itemClickListener: ((String, View) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String) = with(binding) {
        ViewCompat.setTransitionName(ivRv, name)
        root.setOnClickListener {
            itemClickListener?.invoke(name, ivRv)
        }
    }
}