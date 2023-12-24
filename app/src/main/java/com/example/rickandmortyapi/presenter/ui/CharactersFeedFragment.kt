package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentFeedBinding
import com.example.rickandmortyapi.di.daggerComponents.CharacterFeedFragmentComponent
import com.example.rickandmortyapi.di.daggerComponents.DaggerCharacterFeedFragmentComponent
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerListAdapter
import com.example.rickandmortyapi.presenter.feedRecycler.PaginationScrollListener
import com.example.rickandmortyapi.presenter.feedRecycler.CharacterFeedItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.AbstractFeedFragment
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersFeedFragment() : AbstractFeedFragment() {


    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FeedViewModel by viewModels {viewModelFactory}

    override val internetObserverViewModel: InternetConnectionObserverViewModel
    by viewModels {viewModelFactory}


    private val component: CharacterFeedFragmentComponent by lazy{
        DaggerCharacterFeedFragmentComponent.factory().create(requireContext())
    }

    override val moveToDetailsFragmentFun: (id: Int) -> Unit= {
        (activity as MainActivity).moveToDetailsFragment(R.id.fragment_container
            , CharacterDetailsFragment.newInstance(it))
    }

    override val adapter : RecyclerView.Adapter<ViewHolder> by lazy {
        RecyclerListAdapter(listOf<RecyclerItemDelegate>(
            CharacterFeedItemDelegate(moveToDetailsFragmentFun)))
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
        setUpFilterButtonListener()
        setUpNameFilterObserver()
        setUpStatusFilterObserver()
        setUpGenderFilterObserver()
        setOnReloadButtonListener()
        setOnInternetRestoredObserver()
    }

    override fun setUpListStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.charactersList.collect{ listState ->
                    when(listState){
                        is State.Error ->{
                            executeErrorListState()
                        }
                        is State.Empty ->{
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
        viewModel.reloadCharactersList()
    }


    override fun moveToAdapter(data : List<RecyclerModel>?){
            data?.toList()?.let {
                if(viewModel.getCurPage() == 2)
                    (adapter as RecyclerListAdapter).differ.submitList(it)
                else
                    (adapter as RecyclerListAdapter).appendItems(it)
            }
    }

    override fun setUpPaginationScrollListener(layoutManager: LinearLayoutManager) =
        object : PaginationScrollListener(layoutManager){
            override fun isLoading(): Boolean =
                viewModel.charactersList.replayCache.last() is State.Loading

            override fun getNextPage() = viewModel.getCharacters()
            override fun displayedItemsNum() = viewModel.getDisplayedItemsNum()
        }

    override fun executeErrorListState() {
        if(viewModel.charactersList.replayCache.first()
                    is State.Error ||
            viewModel.charactersList.replayCache[1]
                    is State.Error) {
            showSnackBar(getString(R.string.error_message))
            hideProgressBar()
        }
    }

    override fun executeEmptyListState() {
        val recyclerAdapter = binding
            .feedRecycler.adapter as RecyclerListAdapter
        hideProgressBar()
        if(recyclerAdapter
                .differ.currentList.size == 0)
            showEmptyListMessage()
    }

    override fun executeLoadingListState() {
        showProgressBar()
    }

    override fun executeSuccessListState(data : List<RecyclerModel>?) {
        hideProgressBar()
        moveToAdapter(data)
    }


    private fun setUpFilterButtonListener(){
        binding.filterButton.setOnClickListener {
            (activity as? FragmentNavigator)?.moveToDetailsFragment(R.id.container
                , FiltersFragment())

        }
    }

    private fun setUpNameFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nameState.collect {
                    reloadList()
                }
            }
        }
    }

    private fun setUpStatusFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterStatusFilter.collect {
                    reloadList()
                    Log.d("netlist", "filter observer")

                }
            }
        }
    }

    private fun setUpGenderFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterGenderFilter.collect {
                    reloadList()
                }
            }
        }
    }







}