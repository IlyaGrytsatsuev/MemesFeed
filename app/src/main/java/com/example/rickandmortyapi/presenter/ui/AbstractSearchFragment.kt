package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentFiltersBinding
import com.example.rickandmortyapi.databinding.FragmentSearchBinding
import com.example.rickandmortyapi.di.daggerComponents.MainActivityComponent
import com.example.rickandmortyapi.presenter.commonRecyclerUtils.FragmentNavigator

abstract class AbstractSearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding

    protected abstract var viewModelFactory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        setOnTextChangeListener()
        handleFragmentExit()
    }

    private fun setOnTextChangeListener(){
        binding.searchTextField.addTextChangedListener {
            handleInputTextChange(it.toString())
        }
    }

    private fun handleFragmentExit(){
        binding.closeButton.setOnClickListener {
            (activity as FragmentNavigator).handleOnBackPressedNavigation()
        }
    }
    protected abstract fun handleInputTextChange(value:String)
}