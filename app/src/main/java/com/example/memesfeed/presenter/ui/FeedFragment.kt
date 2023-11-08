package com.example.memesfeed.presenter.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.memesfeed.R
import com.example.memesfeed.databinding.FragmentFeedBinding
import com.example.memesfeed.di.MyApp
import com.example.memesfeed.presenter.feedRecycler.FeedItemDelegate
import com.example.memesfeed.presenter.feedRecycler.FeedRecyclerAdapter
import com.example.memesfeed.presenter.feedRecycler.StandartRecyclerFeedItemDelegate
import com.example.memesfeed.presenter.viewmodels.FeedViewModel
import com.example.memesfeed.presenter.viewmodels.FeedViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding

    @Inject
    lateinit var feedViewModelFactory: FeedViewModelFactory

    private var feedViewModel:FeedViewModel? = null

    private var feedRecycler: RecyclerView? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApp).appComponent.inject(this)
        feedViewModel = ViewModelProvider(this, feedViewModelFactory)[FeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        binding = FragmentFeedBinding.bind(view)
        initializeRecycler()
        return view
    }

    private fun initializeRecycler(){
        val delegatesList = listOf<FeedItemDelegate>(
            StandartRecyclerFeedItemDelegate(requireContext()))
        feedRecycler = binding.feedRecycler
        val adapter = FeedRecyclerAdapter(delegatesList)
        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        feedViewModel?.getMemesList()
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