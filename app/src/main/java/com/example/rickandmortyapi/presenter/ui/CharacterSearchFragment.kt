package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.di.daggerComponents.MainActivityComponent
import com.example.rickandmortyapi.presenter.viewmodels.CharactersFeedViewModel
import javax.inject.Inject

class CharacterSearchFragment() :AbstractSearchFragment() {

    val component: MainActivityComponent by lazy {
        (activity as MainActivity).activityComponent
    }
    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CharactersFeedViewModel by activityViewModels {viewModelFactory}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }
    override fun handleInputTextChange(value:String) {
        viewModel.setCharacterNameFilter(value)
    }
}