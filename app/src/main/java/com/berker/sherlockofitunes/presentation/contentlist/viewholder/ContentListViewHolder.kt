package com.berker.sherlockofitunes.presentation.contentlist.viewholder

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.berker.sherlockofitunes.common.extension.executeWithAction
import com.berker.sherlockofitunes.databinding.ItemRvContentBinding
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentItemUiState

class ContentListViewHolder(
    private val binding: ItemRvContentBinding,
    private val itemClickListener: ((String, View) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(state:ContentItemUiState) = with(binding) {
        executeWithAction {
            binding.contentItemUiState = state
        }
        ViewCompat.setTransitionName(ivRv, state.getArtistName() + bindingAdapterPosition)
        tv.text = state.getArtistName()
        root.setOnClickListener {
            itemClickListener?.invoke(state.getArtistName(), ivRv)
        }
    }
}