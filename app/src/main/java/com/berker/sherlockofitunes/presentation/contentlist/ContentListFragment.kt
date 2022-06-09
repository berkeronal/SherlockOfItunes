package com.berker.sherlockofitunes.presentation.contentlist

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.berker.sherlockofitunes.R
import com.berker.sherlockofitunes.core.BaseFragment
import com.berker.sherlockofitunes.databinding.FragmentContentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentListFragment : BaseFragment<FragmentContentListBinding, ContentListViewModel>(
    FragmentContentListBinding::inflate
) {
    override val viewModel: ContentListViewModel by viewModels()

    override fun initUi() {
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_ContentListFragment_to_ContentDetailFragment)
        }
    }
}