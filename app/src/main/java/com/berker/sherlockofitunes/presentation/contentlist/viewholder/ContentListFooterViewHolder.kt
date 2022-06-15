package com.berker.sherlockofitunes.presentation.contentlist.viewholder

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.berker.sherlockofitunes.common.extension.executeWithAction
import com.berker.sherlockofitunes.databinding.ItemRvFooterBinding
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentListFooterUiState

class ContentListFooterViewHolder(
    private val binding: ItemRvFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.executeWithAction {
            footerUiState = ContentListFooterUiState(loadState)
        }
    }
}