package com.example.rickandmortyapi.presenter.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentDetailsBinding
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.DetailsRecyclerAdapter
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.DetailsRecyclerItemDecorator
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.RecyclerItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.InternetConnectionObserverViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class AbstractDetailsFragment: Fragment(R.layout.fragment_details){

    protected lateinit var binding: FragmentDetailsBinding

    protected abstract var viewModelFactory: ViewModelProvider.Factory

    protected abstract val internetObserverViewModel: InternetConnectionObserverViewModel

    protected abstract val delegates: List<RecyclerItemDelegate>

    protected abstract val adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>

    private fun showSnackBarWithAction(message:String, actionMessage:String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).apply {
            setAction(actionMessage){
                reloadDetails()
            }
        }.show()

    }

    protected fun setOnInternetRestoredObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                internetObserverViewModel
                    .connectionState.collect {
                        if (!internetObserverViewModel
                                .connectionState.replayCache.first() && it)
                            showSnackBarWithAction(
                                getString(R.string.internet_available_message),
                                getString(R.string.reload_page_snackbar_button))
                    }
            }
        }
    }

    protected abstract fun setUpDetailsStateObserver()

    protected fun initializeDetailsRecycler(){
        binding.detailsRecycler.adapter = adapter
        binding.detailsRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.detailsRecycler
            .addItemDecoration(DetailsRecyclerItemDecorator())
    }
    abstract fun reloadDetails()

    protected  fun moveToAdapter(data : RecyclerModel?){
        data?.let {
            (binding.detailsRecycler.adapter as DetailsRecyclerAdapter)
                .setCharacterDetailsModel(it)
        }
    }


    protected abstract fun executeErrorState(data: RecyclerModel?)

    protected abstract fun executeEmptyState()

    protected abstract fun executeLoadingState()

    protected abstract fun executeSuccessState
                (data: RecyclerModel?)

    protected fun showEmptyListMessage(){
        binding.emptyStateTextView.visibility = View.VISIBLE
    }
    private fun hideEmptyListMessage(){
        binding.emptyStateTextView.visibility = View.GONE
    }
    protected fun showProgressBar(){
        binding.detailsProgressBar.visibility = View.VISIBLE
    }

    protected fun hideProgressBar(){
        binding.detailsProgressBar.visibility = View.GONE
        hideEmptyListMessage()
    }

    protected fun showSnackBar(message: String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }



}