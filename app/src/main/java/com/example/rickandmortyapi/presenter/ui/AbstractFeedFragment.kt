package com.example.rickandmortyapi.presenter.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentFeedBinding
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FeedItemDecorator
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class AbstractFeedFragment : Fragment(R.layout.fragment_feed) {

    protected lateinit var binding: FragmentFeedBinding

    protected abstract var viewModelFactory: ViewModelProvider.Factory

    protected abstract val internetObserverViewModel: InternetConnectionObserverViewModel

    protected abstract val adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>

    protected abstract val moveToDetailsFragmentFun:(id:Int)->Unit
    private fun showSnackBarWithAction(message:String, actionMessage:String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).apply {
            setAction(actionMessage){
                reloadList()
            }
        }.show()

    }

    protected fun setOnInternetRestoredObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                internetObserverViewModel
                    .connectionState.collect {
                        if (it is InternetState.InternetRestored)
                            showSnackBarWithAction(
                                getString(R.string.internet_available_message),
                                getString(R.string.reload_page_snackbar_button))
                    }
            }
        }
    }

    protected abstract fun setUpListStateObserver()

    protected fun initializeRecycler(){
        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.feedRecycler.adapter = adapter
        binding.feedRecycler.layoutManager = layoutManager
        binding.feedRecycler.addItemDecoration(FeedItemDecorator())
        binding.feedRecycler
            .addOnScrollListener(setUpPaginationScrollListener(layoutManager))
    }
    abstract fun reloadList()

    protected abstract fun moveToAdapter(data : List<RecyclerModel>?)

    protected abstract fun setUpPaginationScrollListener
                (layoutManager: LinearLayoutManager)
    : RecyclerView.OnScrollListener
    protected fun setOnReloadButtonListener(){
        binding.reloadButton.setOnClickListener {
            reloadList()
        }
    }

    protected abstract fun executeErrorListState()

    protected abstract fun executeEmptyListState()

    protected abstract fun executeLoadingListState()

    protected abstract fun executeSuccessListState
                (data : List<RecyclerModel>?)

    protected fun showEmptyListMessage(){
        binding.emptyListTextView.visibility = View.VISIBLE
    }
    private fun hideEmptyListMessage(){
        binding.emptyListTextView.visibility = View.GONE
    }
    protected fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    protected fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
        hideEmptyListMessage()
    }

    protected fun showSnackBar(message: String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }



}