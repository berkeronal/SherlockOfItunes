package com.berker.sherlockofitunes.presentation.contentlist

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berker.sherlockofitunes.common.extension.collect
import com.berker.sherlockofitunes.common.extension.collectLast
import com.berker.sherlockofitunes.common.extension.executeWithAction
import com.berker.sherlockofitunes.core.BaseFragment
import com.berker.sherlockofitunes.databinding.FragmentContentListBinding
import com.berker.sherlockofitunes.presentation.contentlist.adapter.ContentListAdapter
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentItemUiState
import com.berker.sherlockofitunes.presentation.contentlist.uistate.ContentListUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
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
    }

    override fun initReceivers() {
        super.initReceivers()

        collect(contentListAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map {
                it.refresh
            }, ::setViewModelLoadState
        )
        collectLast(viewModel.contentListUiState, ::setUiState)
        collectLast(viewModel.contentListUiState.value.contents, ::setRecyclerViewData)


    }

    private fun setViewModelLoadState(loadState: LoadState) {
        viewModel.setLoadState(loadState)
    }

    private fun setUiState(contentListUiState: ContentListUiState) {
        binding.executeWithAction {
            this.contentListUiState = contentListUiState
        }
    }

    private suspend fun setRecyclerViewData(pagingData: PagingData<ContentItemUiState>) {
        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
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

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rv.layoutManager = layoutManager
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