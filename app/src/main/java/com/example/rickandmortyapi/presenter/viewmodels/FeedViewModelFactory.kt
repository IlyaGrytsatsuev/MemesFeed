package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.domain.usecases.GetCharactersListUseCase
import javax.inject.Inject

class FeedViewModelFactory @Inject constructor(
    private val getCharactersListFromApiUseCase: GetCharactersListUseCase,
    //private val internetConnectionChecker: InternetConnectionChecker,


)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterFeedViewModel(getCharactersListFromApiUseCase,
           // internetConnectionChecker
        ) as T
    }
}