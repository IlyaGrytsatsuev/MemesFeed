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
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.data.PaginationData
import com.example.rickandmortyapi.databinding.FragmentFeedBinding
import com.example.rickandmortyapi.di.MyApp
import com.example.rickandmortyapi.presenter.feedRecycler.FeedItemDelegate
import com.example.rickandmortyapi.presenter.feedRecycler.FeedRecyclerAdapter
import com.example.rickandmortyapi.presenter.feedRecycler.PaginationScrollListener
import com.example.rickandmortyapi.presenter.feedRecycler.CharacterFeedItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.CharacterFeedViewModel
import com.example.rickandmortyapi.presenter.State
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject


class FeedFragment() : Fragment(R.layout.fragment_feed) {

    private lateinit var binding: FragmentFeedBinding

    @Inject
    lateinit var feedViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var paginationData:PaginationData


    private val feedViewModel:CharacterFeedViewModel by viewModels{feedViewModelFactory}

    private var feedRecycler: RecyclerView? = null

    private var snackBar: Snackbar? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedBinding.bind(view)
        initializeRecycler()
        //setUpFilterButtonListener()
    }

    private fun initializeRecycler(){
        val delegatesList = listOf<FeedItemDelegate>(
            CharacterFeedItemDelegate())
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                feedViewModel.charactersList.collect{
                    when(it){
                        is State.Error ->{
                            showSnackBar(getString(R.string.error_message))
                            hideProgressBar()
                        }
                        is State.Loading -> showProgressBar()
                        is State.Success -> {
                            hideProgressBar()
                            it.data?.toList()?.let {
                                    it1 -> adapter.appendItems(it1)
                            }
                        }
                    }
                    if(adapter.itemCount == 0)
                        showEmptyListMessage()
                }
            }
        }
    }

    private fun setUpPaginationScrollListener(layoutManager: LinearLayoutManager) =
        object : PaginationScrollListener(layoutManager){
            override fun isLoading(): Boolean =
                feedViewModel.charactersList.value is State.Loading

            override fun getNextPage() = feedViewModel.getCharacters()
            override fun displayedItemsNum() = paginationData.displayedItemsNum
        }


//    private fun setUpFilterButtonListener(){
//        binding.filterButton.setOnClickListener {
//            childFragmentManager.commit {
//                val fragment = FiltersFragment()
//                replace(R.id.child_container, fragment)
//                setReorderingAllowed(true)
//            }
//
//        }
//    }

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