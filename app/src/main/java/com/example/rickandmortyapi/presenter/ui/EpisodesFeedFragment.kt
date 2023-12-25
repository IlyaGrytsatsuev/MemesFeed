package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentFeedBinding
import com.example.rickandmortyapi.di.daggerComponents.DaggerEpisodesFeedFragmentComponent
import com.example.rickandmortyapi.di.daggerComponents.EpisodesFeedFragmentComponent
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.EpisodesListItemDelegate
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerListAdapter
import com.example.rickandmortyapi.presenter.feedRecycler.PaginationScrollListener
import com.example.rickandmortyapi.presenter.viewmodels.EpisodesFeedViewModel
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesFeedFragment() : AbstractFeedFragment() {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val component: EpisodesFeedFragmentComponent by lazy {
        DaggerEpisodesFeedFragmentComponent.factory()
            .create(requireContext())
    }

    private val viewModel: EpisodesFeedViewModel
            by viewModels { viewModelFactory }

    override val internetObserverViewModel: InternetConnectionObserverViewModel
            by viewModels { viewModelFactory }

    override val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder> by lazy {
        RecyclerListAdapter(
            listOf<RecyclerItemDelegate>(
                EpisodesListItemDelegate()
            )
        )
    }

    override val moveToDetailsFragmentFun: (id: Int) -> Unit = {
        //TODO
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedBinding.bind(view)
        initializeRecycler()
        setUpListStateObserver()
        setOnReloadButtonListener()
        setOnInternetRestoredObserver()
    }


    override fun setUpListStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.episodesList.collect { listState ->
                    when (listState) {
                        is State.Error -> {
                            executeErrorListState()
                        }
                        is State.Empty -> {
                            executeEmptyListState()
                        }
                        is State.Loading ->
                            executeLoadingListState()
                        is State.Success -> {
                            executeSuccessListState(listState.data)
                        }

                    }
                }
            }
        }
    }

    override fun reloadList() {
        binding.feedRecycler.scrollToPosition(0)
        viewModel.reloadEpisodesList()
    }

    override fun moveToAdapter(data: List<RecyclerModel>?) {
        data?.toList()?.let {
            if (viewModel.getCurPage() == 2)
                (adapter as RecyclerListAdapter).differ.submitList(it)
            else
                (adapter as RecyclerListAdapter).appendItems(it)
        }
    }

    override fun setUpPaginationScrollListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener =
        object : PaginationScrollListener(layoutManager) {
            override fun isLoading(): Boolean =
                viewModel.episodesList.replayCache.last() is State.Loading

            override fun getNextPage() = viewModel.getEpisodes()
            override fun displayedItemsNum() = viewModel.getDisplayedItemsNum()
        }

    override fun executeErrorListState() {
        if (viewModel.episodesList.replayCache.first()
                    is State.Error ||
            viewModel.episodesList.replayCache[1]
                    is State.Error
        ) {
            showSnackBar(getString(R.string.error_message))
            hideProgressBar()
        }
    }
    override fun executeEmptyListState() {
        val recyclerAdapter = binding
            .feedRecycler.adapter as RecyclerListAdapter
        hideProgressBar()
        if (recyclerAdapter
                .differ.currentList.size == 0
        )
            showEmptyListMessage()
    }
    override fun executeLoadingListState() {
        showProgressBar()
    }
    override fun executeSuccessListState(data: List<RecyclerModel>?) {
        hideProgressBar()
        moveToAdapter(data)
    }

}