package com.berker.sherlockofitunes.presentation.contentlist

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.berker.sherlockofitunes.common.extension.collect
import com.berker.sherlockofitunes.core.BaseFragment
import com.berker.sherlockofitunes.databinding.FragmentContentListBinding
import com.berker.sherlockofitunes.domain.model.Content
import com.berker.sherlockofitunes.presentation.contentlist.adapter.ContentListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContentListFragment : BaseFragment<FragmentContentListBinding, ContentListViewModel>(
    FragmentContentListBinding::inflate
) {
    override val viewModel: ContentListViewModel by viewModels()

    @Inject
    lateinit var contentListAdapter: ContentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        super.onCreate(savedInstanceState)
    }

    override fun initUi() {
        viewModel.getContent()

        initAdapter()
        initRecyclerView()
        postponeEnterTransition()
        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
        ViewCompat.setTransitionName(binding.imageView, "imageA")

        binding.buttonFirst.setOnClickListener {

        }
    }

    override fun initReceivers() {
        super.initReceivers()

        collect(viewModel.contentFlow, ::setRecyclerViewData)
    }

    private suspend fun setRecyclerViewData(pagingData: PagingData<Content>) {
        contentListAdapter.submitData(pagingData)

        if (contentListAdapter.itemCount == 0) {

        }
    }

    private fun initAdapter() {
        contentListAdapter.apply {
            setItemClickListener { id, view ->
                navigateWithTransition(view, id)

            }
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private fun initRecyclerView() {
        binding.adapter = contentListAdapter
    }

    private fun navigateWithTransition(view: View, id: String) {
        val extras = FragmentNavigatorExtras(
            view to "imageB"
        )
        findNavController().navigate(
            ContentListFragmentDirections.actionContentListFragmentToContentDetailFragment(id),
            extras
        )
    }
}