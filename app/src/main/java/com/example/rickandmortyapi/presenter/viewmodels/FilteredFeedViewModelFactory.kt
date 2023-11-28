package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.data.network.InternetConnectionChecker
import com.example.rickandmortyapi.domain.usecases.GetCharactersFromDbUsecase
import com.example.rickandmortyapi.domain.usecases.GetCharactersListFromApiUseCase
import com.example.rickandmortyapi.domain.usecases.UpsertCharactersIntoDbUsecase
import javax.inject.Inject

class FilteredFeedViewModelFactory @Inject constructor(
    private val getCharactersListFromApiUseCase: GetCharactersListFromApiUseCase,
    private val internetConnectionChecker: InternetConnectionChecker,
    private val getCharactersFromDbUsecase: GetCharactersFromDbUsecase,
    private val upsertCharactersIntoDbUsecase: UpsertCharactersIntoDbUsecase
)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilteredFeedViewModel(getCharactersListFromApiUseCase,
            internetConnectionChecker,
            getCharactersFromDbUsecase,
            upsertCharactersIntoDbUsecase) as T
    }
}