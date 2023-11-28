package com.example.rickandmortyapi.presenter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.usecases.GetCharactersListUseCase
import com.example.rickandmortyapi.presenter.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class CharacterFeedViewModel (
    private val getCharactersListUseCase: GetCharactersListUseCase,
    //private val internetConnectionChecker: InternetConnectionChecker,
): ViewModel() {

    private val privateCharactersList:
            MutableStateFlow<State<List<CharacterModel>>>
    = MutableStateFlow(State.Loading())

    val charactersList:StateFlow<State<List<CharacterModel>>> = privateCharactersList


    init{
        getCharacters()
    }


    fun getCharacters(){
        privateCharactersList.value = State.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                privateCharactersList.value = State
                    .Success(getCharactersListUseCase.execute())
            }
            catch (e:Exception){
                privateCharactersList.value = State
                    .Error()
            }
        }
    }

//    private fun checkInternetConnection() =
//        viewModelScope.launch {
//            privateCharactersList.value = internetConnectionChecker.checkInternetConnection()
//        }


}