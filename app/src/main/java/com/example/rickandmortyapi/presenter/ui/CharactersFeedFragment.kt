package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.data.db.converters.toDbEntity
import com.example.rickandmortyapi.databinding.FragmentFeedBinding
import com.example.rickandmortyapi.di.daggerComponents.MainActivityComponent
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerListAdapter
import com.example.rickandmortyapi.presenter.feedRecycler.PaginationScrollListener
import com.example.rickandmortyapi.presenter.feedRecycler.CharacterFeedItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.CharactersFeedViewModel
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersFeedFragment() : AbstractFeedFragment() {


    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CharactersFeedViewModel by activityViewModels  {viewModelFactory}

    override val internetObserverViewModel: InternetConnectionObserverViewModel
    by viewModels {viewModelFactory}


    private val component: MainActivityComponent by lazy{
        (activity as MainActivity).activityComponent
    }

    override val moveToDetailsFragmentFun: (id: Int) -> Unit= {
        (activity as MainActivity).moveToChildFragment(R.id.child_container
            , CharacterDetailsFragment.newInstance(it))
    }

    override val adapter : RecyclerView.Adapter<ViewHolder> by lazy {
        RecyclerListAdapter(listOf<RecyclerItemDelegate>(
            CharacterFeedItemDelegate(moveToDetailsFragmentFun)))
    }

    override fun onDestroy() {
        super.onDestroy()
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
        setUpSearchButtonListener()
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
        binding.appBarLayout.setExpanded(true)
        viewModel.reloadCharactersList()
    }


    override fun moveToAdapter(data : List<RecyclerModel>?){
            data?.toList()?.let {
                if(viewModel.getCurPage() == 2)
                    (adapter as RecyclerListAdapter).submitList(it)
                else
                    (adapter as RecyclerListAdapter).appendItems(it)
            }
    }

    override fun setUpPaginationScrollListener(layoutManager: LinearLayoutManager) =
        object : PaginationScrollListener(layoutManager){
            override fun isLoading(): Boolean =
                viewModel.isLoadingState()

            override fun getNextPage() = viewModel.getCharacters()
            override fun displayedItemsNum() = viewModel.getDisplayedItemsNum()
        }

    override fun executeErrorListState() {
        if(!viewModel.isRepeatedErrorState())
            showSnackBar(getString(R.string.error_message))
        hideProgressBar()
    }

    override fun executeEmptyListState() {
        val recyclerAdapter = binding
            .feedRecycler.adapter as RecyclerListAdapter
        hideProgressBar()
        if(recyclerAdapter.isListEmpty())
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
        binding.filterButton.visibility = View.VISIBLE
        binding.filterButton.setOnClickListener {
            (activity as? FragmentNavigator)?.moveToChildFragment(R.id.container
                , CharacterFiltersFragment())

        }
    }

    private fun setUpSearchButtonListener(){
        binding.searchButton.visibility = View.VISIBLE
        binding.searchButton.setOnClickListener {
            (activity as? FragmentNavigator)
                ?.moveToChildFragment(R.id.container,
                    CharacterSearchFragment())
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
        viewLifecycleOwner.lifecycleScope.launch() {
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