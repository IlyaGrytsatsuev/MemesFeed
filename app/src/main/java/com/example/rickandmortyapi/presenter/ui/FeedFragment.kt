package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
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
import com.example.rickandmortyapi.presenter.feedRecycler.FeedItemDelegate
import com.example.rickandmortyapi.presenter.feedRecycler.FeedRecyclerAdapter
import com.example.rickandmortyapi.presenter.feedRecycler.StandartRecyclerFeedItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding

    @Inject
    lateinit var feedViewModelFactory: ViewModelProvider.Factory


    val feedViewModel:FeedViewModel by viewModels{feedViewModelFactory}

    private var feedRecycler: RecyclerView? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        binding = FragmentFeedBinding.bind(view)
        initializeRecycler()
        feedViewModel?.getMemesList()
        return view
    }

    private fun initializeRecycler(){
        val delegatesList = listOf<FeedItemDelegate>(
            StandartRecyclerFeedItemDelegate(requireContext()))
        feedRecycler = binding.feedRecycler
        val adapter = FeedRecyclerAdapter(delegatesList)
        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        setUpMemesListObserver(adapter)
        feedRecycler?.adapter = adapter
        feedRecycler?.layoutManager = layoutManager
    }
    private fun setUpMemesListObserver(adapter: FeedRecyclerAdapter){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                feedViewModel?.memeslist?.collect{
                    adapter.differ.submitList(it)
                }
            }
        }
    }


}