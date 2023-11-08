package com.example.memesfeed.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memesfeed.data.network.responseModels.Meme
import com.example.memesfeed.domain.models.MemeModel
import com.example.memesfeed.domain.usecases.GetMemesListFromApiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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


}