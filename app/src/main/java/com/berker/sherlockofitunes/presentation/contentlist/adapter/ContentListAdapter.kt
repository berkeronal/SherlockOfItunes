package com.berker.sherlockofitunes.presentation.contentlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.berker.sherlockofitunes.databinding.ItemRvContentBinding
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentItemUiState
import com.berker.sherlockofitunes.presentation.contentlist.viewholder.ContentListViewHolder
import javax.inject.Inject

class ContentListAdapter @Inject constructor(
) : PagingDataAdapter<ContentItemUiState, ContentListViewHolder>(ContentComparator) {

    companion object{
        const val ITEM_SIZE = 2
        const val FOOTER_SIZE = 1
        const val DEFAULT_SPAN_SIZE = 2
    }

    private var itemClickListener: ((String, View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentListViewHolder {
        val itemBinding =
            ItemRvContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentListViewHolder(itemBinding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ContentListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    object ContentComparator : DiffUtil.ItemCallback<ContentItemUiState>() {
        override fun areItemsTheSame(oldItem: ContentItemUiState, newItem: ContentItemUiState) =
            oldItem.artistName == newItem.artistName && oldItem.trackId == newItem.trackId && oldItem.collectionName == newItem.collectionName

        override fun areContentsTheSame(oldItem: ContentItemUiState, newItem: ContentItemUiState) =
            oldItem == newItem
    }

    fun setItemClickListener(listener: (String, View) -> Unit) {
        itemClickListener = listener
    }
}