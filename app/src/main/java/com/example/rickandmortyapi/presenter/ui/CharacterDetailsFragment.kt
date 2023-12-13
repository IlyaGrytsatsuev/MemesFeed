package com.example.rickandmortyapi.presenter.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmortyapi.di.MyApp
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.presenter.RecyclerItemDelegate
import com.example.rickandmortyapi.presenter.viewmodels.CharacterDetailsViewModel
import com.example.rickandmortyapi.presenter.viewmodels.FeedViewModel
import com.example.rickandmortyapi.presenter.viewmodels.MultiViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject
import com.example.rickandmortyapi.presenter.CharacterDetailsRecycler.CharacterParameterItemDelegate


class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private lateinit var binding: FragmentCharacterDetailsBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : FeedViewModel by viewModels { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailsBinding.bind(view)


    }

    private fun initializeCharacterDetailsRecycler(){
        val delegates = mutableListOf<RecyclerItemDelegate>(CharacterParameterItemDelegate(),
            CharacterParameterItemDelegate()
        )

    }

}