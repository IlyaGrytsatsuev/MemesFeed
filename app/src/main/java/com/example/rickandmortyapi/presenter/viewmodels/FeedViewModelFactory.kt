package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapi.domain.usecases.GetCharactersListUseCase
import javax.inject.Inject
import javax.inject.Provider


class FeedViewModelFactory @Inject constructor(
    private val viewModelFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    //private val getCharactersListFromApiUseCase: GetCharactersListUseCase,
    //private val internetConnectionChecker: InternetConnectionChecker,


)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelFactories.getValue(modelClass as Class<ViewModel>).get() as T
//        return FeedViewModel(getCharactersListFromApiUseCase,
//           // internetConnectionChecker
//        ) as T
    }
}