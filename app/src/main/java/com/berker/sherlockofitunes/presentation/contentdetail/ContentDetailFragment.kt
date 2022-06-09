package com.berker.sherlockofitunes.presentation.contentdetail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.berker.sherlockofitunes.R
import com.berker.sherlockofitunes.core.BaseFragment
import com.berker.sherlockofitunes.databinding.FragmentContentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentDetailFragment : BaseFragment<FragmentContentDetailBinding, ContentDetailViewModel>(
    FragmentContentDetailBinding::inflate
) {
    override val viewModel: ContentDetailViewModel by viewModels()

    override fun initUi() {
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_ContentDetailFragment_to_ContentListFragment)
        }
    }

}