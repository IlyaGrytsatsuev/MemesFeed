package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.rickandmortyapi.di.MyApp
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerListAdapter
import com.example.rickandmortyapi.presenter.feedRecycler.PaginationScrollListener
import com.example.rickandmortyapi.presenter.feedRecycler.CharacterFeedItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator
import com.example.rickandmortyapi.presenter.viewmodels.CharacterDetailsViewModel
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

    private val characterDetailsViewModel: CharacterDetailsViewModel
    by viewModels {viewModelFactory}


    private var feedRecycler: RecyclerView? = null //todo remove

    private var snackBar: Snackbar? = null

    private val adapter : RecyclerView.Adapter<ViewHolder> by lazy {
        RecyclerListAdapter(listOf<RecyclerItemDelegate>(
            CharacterFeedItemDelegate(moveToCharacterDetailsFragmentFun)))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApp).appComponent.inject(this)
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
        feedRecycler = binding.feedRecycler
        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        setUpCharactersListStateObserver()
        feedRecycler?.adapter = adapter
        feedRecycler?.layoutManager = layoutManager
        feedRecycler?.addOnScrollListener(setUpPaginationScrollListener(layoutManager))

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
                if(viewModel.getCurPage() == 2)
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
            viewModel.reloadCharactersList()
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
                    viewModel.reloadCharactersList()
                }
            }
        }
    }

    private fun setUpStatusFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterStatusFilter.collect {
                    viewModel.reloadCharactersList()
                }
            }
        }
    }

    private fun setUpGenderFilterObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterGenderFilter.collect {
                    viewModel.reloadCharactersList()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("net","onPause() is called")
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
        snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snackBar?.show()
    }

    //todo remove
    private val moveToCharacterDetailsFragmentFun:(characterId:Int)->Unit = {
        characterDetailsViewModel.setCharacterId(it)
        characterDetailsViewModel.getCharacterDetails()
        (activity as MainActivity).moveToFragment(R.id.fragment_container
            , CharacterDetailsFragment())
    }

}