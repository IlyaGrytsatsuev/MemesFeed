package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.domain.usecases.GetMemesListFromApiUseCase
import javax.inject.Inject

class FeedViewModelFactory @Inject constructor(
    val getMemesListFromApiUseCase: GetMemesListFromApiUseCase)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedViewModel(getMemesListFromApiUseCase) as T
    }
}