package com.berker.sherlockofitunes.presentation.contentlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berker.sherlockofitunes.databinding.ItemRvContentBinding

class ContentListAdapter(
    var itemClickListener: ((String, View) -> Unit)
) : RecyclerView.Adapter<ContentListViewHolder>() {

    private var itemList: List<String> = listOf("Berker", "Mehmet", "1", "2", "3")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentListViewHolder {
        val itemBinding =
            ItemRvContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentListViewHolder(itemBinding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ContentListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

}