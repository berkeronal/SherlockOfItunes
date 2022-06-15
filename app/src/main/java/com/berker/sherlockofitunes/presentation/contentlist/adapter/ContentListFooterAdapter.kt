package com.berker.sherlockofitunes.presentation.contentlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.berker.sherlockofitunes.R
import com.berker.sherlockofitunes.databinding.ItemRvFooterBinding
import com.berker.sherlockofitunes.presentation.contentlist.viewholder.ContentListFooterViewHolder

class ContentListFooterAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<ContentListFooterViewHolder>() {
    override fun onBindViewHolder(holder: ContentListFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ContentListFooterViewHolder {
        val itemRvFooterBinding = DataBindingUtil.inflate<ItemRvFooterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rv_footer,
            parent,
            false
        )
        return ContentListFooterViewHolder(itemRvFooterBinding, retry = retry)
    }

}