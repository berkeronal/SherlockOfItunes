package com.berker.sherlockofitunes.presentation.contentlist.viewholder

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.berker.sherlockofitunes.common.extension.executeWithAction
import com.berker.sherlockofitunes.databinding.ItemRvContentBinding
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentItemUiState

class ContentListViewHolder(
    private val binding: ItemRvContentBinding,
    private val itemClickListener: ((String, View, ContentItemUiState) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(state: ContentItemUiState) = with(binding) {
        executeWithAction {
            binding.contentItemUiState = state
        }
        val transitionName = state.artistName + state.collectionName + bindingAdapterPosition
        setTransitions(transitionName, binding.root)
        setTransitions(transitionName + "h", tvHeader)
        setTransitions(transitionName + "c", tvCollectionName)

        root.setOnClickListener {
            itemClickListener?.invoke(state.artistName, binding.root, state)
        }
        cardView.setOnClickListener {
            itemClickListener?.invoke(state.artistName, binding.root, state)
        }
    }

    private fun setTransitions(id: String, view: View) {
        ViewCompat.setTransitionName(
            view, id
        )
    }
}