package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.example.rickandmortyapi.data.PaginationData
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
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator
import com.example.rickandmortyapi.presenter.feedRecycler.CharacterFeedItemDecorator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject


class FeedFragment : Fragment(R.layout.fragment_feed) {

    private lateinit var binding: FragmentFeedBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var paginationData:PaginationData

    private val viewModel: FeedViewModel by viewModels {viewModelFactory}

    private val component: CharacterFeedFragmentComponent by lazy{
        DaggerCharacterFeedFragmentComponent.factory().create(requireContext())
    }

    private val moveToCharacterDetailsFragmentFun:(characterId:Int)->Unit = {
        (activity as MainActivity).moveToFragment(R.id.fragment_container
            , CharacterDetailsFragment.newInstance(it))
    }

    private val adapter : RecyclerView.Adapter<ViewHolder> by lazy {
        RecyclerListAdapter(listOf<RecyclerItemDelegate>(
            CharacterFeedItemDelegate(moveToCharacterDetailsFragmentFun)))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedBinding.bind(view)
        initializeRecycler()
        setUpFilterButtonListener()
        setUpNameFilterObserver()
        setUpStatusFilterObserver()
        setUpGenderFilterObserver()
        setOnReloadButtonListener()
    }

    private fun initializeRecycler(){

        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        setUpCharactersListStateObserver()
        binding.feedRecycler.adapter = adapter
        binding.feedRecycler.layoutManager = layoutManager
        binding.feedRecycler.addItemDecoration(CharacterFeedItemDecorator())
        binding.feedRecycler
            .addOnScrollListener(setUpPaginationScrollListener(layoutManager))

    }
    private fun setUpCharactersListStateObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.charactersList.collect{
                    when(it){
                        is State.Error ->{
                            showSnackBar(getString(R.string.error_message))
                            hideProgressBar()
                        }
                        is State.Empty ->{
                            hideProgressBar()
                            showEmptyListMessage()
                        }
                        is State.Loading -> showProgressBar()
                        is State.Success -> {
                            hideProgressBar()
                            moveToAdapter(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun moveToAdapter(data : List<RecyclerModel>?){
            data?.toList()?.let {
                if(viewModel.getCurPage() == 2 || viewModel.getCurPage() == 1)
                    (adapter as RecyclerListAdapter).differ.submitList(it)
                else
                    (adapter as RecyclerListAdapter).appendItems(it)
            }
    }

    private fun setUpPaginationScrollListener(layoutManager: LinearLayoutManager) =
        object : PaginationScrollListener(layoutManager){
            override fun isLoading(): Boolean =
                viewModel.charactersList.value is State.Loading

            override fun getNextPage() = viewModel.getCharacters()
            override fun displayedItemsNum() = viewModel.getDisplayedItemsNum()
        }


    private fun setOnReloadButtonListener(){
        binding.reloadButton.setOnClickListener {
            reloadCharactersList()
        }
    }
    private fun setUpFilterButtonListener(){
        binding.filterButton.setOnClickListener {
            (activity as? FragmentNavigator)?.moveToFragment(R.id.container
                , FiltersFragment())

        }
    }

    private fun setUpNameFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nameState.collect {
                    reloadCharactersList()
                }
            }
        }
    }

    private fun setUpStatusFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterStatusFilter.collect {
                    reloadCharactersList()
                }
            }
        }
    }

    private fun setUpGenderFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterGenderFilter.collect {
                    reloadCharactersList()
                }
            }
        }
    }


    private fun reloadCharactersList(){
        binding.feedRecycler.scrollToPosition(0)
        viewModel.reloadCharactersList()
    }

    private fun showEmptyListMessage(){
        binding.emptyListTextView.visibility = View.VISIBLE
    }
    private fun hideEmptyListMessage(){
        binding.emptyListTextView.visibility = View.GONE
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
        hideEmptyListMessage()
    }

    private fun showSnackBar(message: String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }



}