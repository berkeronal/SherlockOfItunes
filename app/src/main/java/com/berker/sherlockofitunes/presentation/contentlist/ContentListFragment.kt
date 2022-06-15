package com.berker.sherlockofitunes.presentation.contentlist

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.berker.sherlockofitunes.common.extension.collect
import com.berker.sherlockofitunes.common.extension.collectLast
import com.berker.sherlockofitunes.common.extension.executeWithAction
import com.berker.sherlockofitunes.core.BaseFragment
import com.berker.sherlockofitunes.databinding.FragmentContentListBinding
import com.berker.sherlockofitunes.domain.model.ContentType
import com.berker.sherlockofitunes.presentation.contentdetail.ContentDetailFragment.Companion.SHARED_REFERENCE
import com.berker.sherlockofitunes.presentation.contentlist.adapter.ContentListAdapter
import com.berker.sherlockofitunes.presentation.contentlist.adapter.ContentListAdapter.Companion.DEFAULT_SPAN_SIZE
import com.berker.sherlockofitunes.presentation.contentlist.adapter.ContentListAdapter.Companion.FOOTER_SIZE
import com.berker.sherlockofitunes.presentation.contentlist.adapter.ContentListAdapter.Companion.ITEM_SIZE
import com.berker.sherlockofitunes.presentation.contentlist.adapter.ContentListFooterAdapter
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
        initFilterButtonChangeListener()
        postponeEnterTransition()
    }

    private fun initFilterButtonChangeListener() {
        binding.fbgMediaType.changeListener = { clickedText ->
            val contentType = ContentType.values().filter { it.value == clickedText }

            viewModel.onEvent(
                ContentListUiEvent.OnContentTypeChanged(
                    contentType.firstOrNull() ?: ContentType.Movie
                )
            )
        }
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

        binding.etvTerm.addTextChangedListener {
            viewModel.onEvent(ContentListUiEvent.OnTermChanged(it.toString()))
        }

    }

    private fun setViewModelLoadState(loadState: LoadState) {
        viewModel.setLoadState(loadState)
    }

    private suspend fun setUiState(contentListUiState: ContentListUiState) {
        binding.executeWithAction {
            this.contentListUiState = contentListUiState
        }
        binding.etvTerm.setSelection(contentListUiState.term.length)
        collectLast(contentListUiState.contents, ::setRecyclerViewData)
    }

    private suspend fun setRecyclerViewData(pagingData: PagingData<ContentItemUiState>) {
        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
        contentListAdapter.submitData(pagingData)
    }

    private fun initAdapter() {
        contentListAdapter.apply {
            setItemClickListener { id, root, state ->
                navigateWithTransition(root, id, state)
            }
            addLoadStateListener { loadState ->
                with(binding) {
                    when (decideState(loadState = loadState)) {
                        SearchState.NO_TYPE -> {
                            rvContent.isVisible = false
                            tvContent.isVisible = false
                            tvStartSearch.isVisible = true
                        }
                        SearchState.NO_CONTENT -> {
                            rvContent.isVisible = false
                            tvContent.isVisible = true
                            tvStartSearch.isVisible = false
                        }
                        SearchState.CONTENT -> {
                            rvContent.isVisible = true
                            tvContent.isVisible = false
                            tvStartSearch.isVisible = false
                        }
                    }
                }
            }
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private fun decideState(loadState: CombinedLoadStates): SearchState {
        return if (binding.contentListUiState?.term?.isEmpty() == true) SearchState.NO_TYPE
        else if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && contentListAdapter.itemCount < 1) SearchState.NO_TYPE
        else SearchState.CONTENT
    }

    private fun initRecyclerView() = with(binding) {
        adapter =
            contentListAdapter.withLoadStateFooter(
                ContentListFooterAdapter(
                    contentListAdapter::retry
                )
            )

        val layoutManager = GridLayoutManager(requireContext(), DEFAULT_SPAN_SIZE).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {

                    return if (contentListAdapter.itemCount > position) FOOTER_SIZE else ITEM_SIZE
                }
            }
        }
        rvContent.layoutManager = layoutManager
    }

    private fun navigateWithTransition(
        root: View,
        id: String,
        state: ContentItemUiState
    ) {

        val content = viewModel.getContentByContentListUiState(state)
        val extras = FragmentNavigatorExtras(
            root to SHARED_REFERENCE
        )
        findNavController().navigate(
            ContentListFragmentDirections.actionContentListFragmentToContentDetailFragment(
                sharedElementName = id,
                contentModel = content
            ),
            extras
        )
    }
}