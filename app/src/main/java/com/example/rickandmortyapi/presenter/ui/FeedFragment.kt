package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentFeedBinding
import com.example.rickandmortyapi.di.MyApp
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.presenter.feedRecycler.FeedItemDelegate
import com.example.rickandmortyapi.presenter.feedRecycler.FeedRecyclerAdapter
import com.example.rickandmortyapi.presenter.feedRecycler.PaginationScrollListener
import com.example.rickandmortyapi.presenter.feedRecycler.StandartRecyclerFeedItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import com.example.rickandmortyapi.utils.State
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject


class FeedFragment() : Fragment(R.layout.fragment_feed) {

    private lateinit var binding: FragmentFeedBinding

    @Inject
    lateinit var feedViewModelFactory: ViewModelProvider.Factory


    private val feedViewModel:FeedViewModel by viewModels{feedViewModelFactory}

    private var feedRecycler: RecyclerView? = null

    var snackBar: Snackbar? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedBinding.bind(view)
        feedViewModel.getCharacters()
        initializeRecycler()
    }

    private fun initializeRecycler(){
        val delegatesList = listOf<FeedItemDelegate>(
            StandartRecyclerFeedItemDelegate(requireContext()))
        feedRecycler = binding.feedRecycler
        val adapter = FeedRecyclerAdapter(delegatesList)
        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        setUpCharactersListStateObserver(adapter)
        feedRecycler?.adapter = adapter
        feedRecycler?.layoutManager = layoutManager
        feedRecycler?.addOnScrollListener(setUpPaginationScrollListener(layoutManager))
    }
    private fun setUpCharactersListStateObserver(adapter: FeedRecyclerAdapter){
        var curList: List<CharacterModel>? = mutableListOf()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                feedViewModel.charactersList.collect{
                    when(it){
                        is State.NoInternet -> {showSnackBar(it.message?:"")
                        hideProgressBar()}
                        is State.DbLoading -> showProgressBar()
                        is State.DbSuccess -> {hideProgressBar()
                        curList = it.data?.toList()}
                        is State.DbEmpty -> showEmptyListMessage()
                        is State.NetworkLoading -> showProgressBar()
                        is State.NetworkSuccess -> {hideProgressBar()
                            curList = it.data?.toList()
                        }

                        is State.NetworkError -> showSnackBar(it.message?:"")
                    }
                    Log.d("listDB", curList.toString())

                    curList?.let { it1 -> adapter.appendCharacters(it1) }
                }
            }
        }
    }

    private fun setUpPaginationScrollListener(layoutManager: LinearLayoutManager) =
        object : PaginationScrollListener(layoutManager){
            override fun isLoading(): Boolean =
                feedViewModel.charactersList.value is State.NetworkLoading

            override fun hasInternetConnection(): Boolean =
                feedViewModel.charactersList.value !is State.NoInternet

            override fun getNextPage() = feedViewModel.getCharacters()
            override fun itemsNumInCache(): Int {
                TODO("Not yet implemented")
            }
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


}