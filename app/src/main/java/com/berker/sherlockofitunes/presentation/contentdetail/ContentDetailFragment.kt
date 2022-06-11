package com.berker.sherlockofitunes.presentation.contentdetail

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.berker.sherlockofitunes.R
import com.berker.sherlockofitunes.core.BaseFragment
import com.berker.sherlockofitunes.databinding.FragmentContentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentDetailFragment : BaseFragment<FragmentContentDetailBinding, ContentDetailViewModel>(
    FragmentContentDetailBinding::inflate
) {
    override val viewModel: ContentDetailViewModel by viewModels()
    private val args: ContentDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        super.onCreate(savedInstanceState)
    }

    override fun initUi() {
        ViewCompat.setTransitionName(binding.iv , "imageB")

        binding.buttonSecond.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.iv to args.sharedElementName
            )
            findNavController().navigate(R.id.action_ContentDetailFragment_to_ContentListFragment,null,null,extras)

        }
    }

}