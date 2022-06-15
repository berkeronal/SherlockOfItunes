package com.berker.sherlockofitunes.presentation.contentdetail

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.berker.sherlockofitunes.common.extension.executeWithAction
import com.berker.sherlockofitunes.core.BaseFragment
import com.berker.sherlockofitunes.databinding.FragmentContentDetailBinding
import com.berker.sherlockofitunes.presentation.contentdetail.uistate.ContentDetailUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContentDetailFragment : BaseFragment<FragmentContentDetailBinding, ContentDetailViewModel>(
    FragmentContentDetailBinding::inflate
) {
    override val viewModel: ContentDetailViewModel by viewModels()
    private val args: ContentDetailFragmentArgs by navArgs()

    companion object {
        const val SHARED_REFERENCE = "Root"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        super.onCreate(savedInstanceState)
    }

    override fun initUi() {
        binding.executeWithAction {
            contentDetailUiState = ContentDetailUiState(args.contentModel)
        }
        ViewCompat.setTransitionName(binding.root, SHARED_REFERENCE)
        viewModel.setContent(args.contentModel)
    }

}