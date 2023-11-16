package com.example.rickandmortyapi.presenter.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.domain.usecases.CheckInternetConnectionUsecase
import com.example.rickandmortyapi.domain.usecases.GetCharactersFromDbUsecase
import com.example.rickandmortyapi.domain.usecases.GetCharactersListFromApiUseCase
import com.example.rickandmortyapi.domain.usecases.UpsertCharactersIntoDbUsecase
import javax.inject.Inject

class FeedViewModelFactory @Inject constructor(
    private val getCharactersListFromApiUseCase: GetCharactersListFromApiUseCase,
    private val checkInternetConnectionUsecase: CheckInternetConnectionUsecase,
    private val getCharactersFromDbUsecase: GetCharactersFromDbUsecase,
    private val upsertCharactersIntoDbUsecase: UpsertCharactersIntoDbUsecase

)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedViewModel(getCharactersListFromApiUseCase,
            checkInternetConnectionUsecase,
            getCharactersFromDbUsecase,
            upsertCharactersIntoDbUsecase) as T
    }
}