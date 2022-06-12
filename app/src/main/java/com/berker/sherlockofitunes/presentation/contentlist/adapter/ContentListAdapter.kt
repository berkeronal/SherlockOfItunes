package com.berker.sherlockofitunes.presentation.contentlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.berker.sherlockofitunes.databinding.ItemRvContentBinding
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.presentation.contentlist.viewholder.ContentListViewHolder
import javax.inject.Inject

class ContentListAdapter @Inject constructor(
) : PagingDataAdapter<Content, ContentListViewHolder>(ContentComparator()) {

    private var itemList: List<String> = listOf("Berker", "Mehmet", "1", "2", "3")
    private var itemClickListener: ((String, View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentListViewHolder {
        val itemBinding =
            ItemRvContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentListViewHolder(itemBinding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ContentListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    private class ContentComparator : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldItem: Content, newItem: Content) =
            oldItem.trackId == newItem.trackId

        override fun areContentsTheSame(oldItem: Content, newItem: Content) =
            oldItem == newItem
    }

    fun setItemClickListener(listener: (String, View) -> Unit) {
        itemClickListener = listener
    }

}