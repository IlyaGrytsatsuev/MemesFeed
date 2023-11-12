package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.usecases.GetMemesListFromApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel (
    private val getMemesListFromApiUseCase: GetMemesListFromApiUseCase
): ViewModel() {

    private val privateMemesList: MutableStateFlow<List<MemeModel>> = MutableStateFlow(emptyList())

    val memeslist:StateFlow<List<MemeModel>> = privateMemesList

    fun getMemesList(){
        viewModelScope.launch {
            privateMemesList.value = getMemesListFromApiUseCase.execute()
        }
    }


    sealed interface State {

    }
}