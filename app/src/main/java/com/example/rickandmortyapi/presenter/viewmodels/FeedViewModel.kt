package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.usecases.CheckInternetConnectionUsecase
import com.example.rickandmortyapi.domain.usecases.GetCharactersFromDbUsecase
import com.example.rickandmortyapi.domain.usecases.GetCharactersListFromApiUseCase
import com.example.rickandmortyapi.domain.usecases.UpsertCharactersIntoDbUsecase
import com.example.rickandmortyapi.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel (
    private val getCharactersListFromApiUseCase: GetCharactersListFromApiUseCase,
    private val checkInternetConnectionUsecase: CheckInternetConnectionUsecase,
    private val getCharactersFromDbUsecase: GetCharactersFromDbUsecase,
    private val upsertCharactersIntoDbUsecase: UpsertCharactersIntoDbUsecase
): ViewModel() {

    private val privateCharactersList:
            MutableStateFlow<State<List<CharacterModel>>>
    = MutableStateFlow(State.DbLoading())

    val charactersList:StateFlow<State<List<CharacterModel>>> = privateCharactersList

    private val apiRequestQueue: List<()->Unit> = mutableListOf()

    private var curPage: Int = 1
    private var totalPageNum: Int = 2

    private fun getCharactersFromApi() =
        viewModelScope.launch(Dispatchers.IO) {
            privateCharactersList.value = State.NetworkLoading()
            //Log.d("listNetwork", "loading page $curPage")
            privateCharactersList.value = getCharactersListFromApiUseCase.execute(curPage)
            totalPageNum = privateCharactersList.value.info?.pages?:0
            curPage++
        }


   private fun getCharactersFromDb() =
        viewModelScope.launch(Dispatchers.IO){
            privateCharactersList.value = getCharactersFromDbUsecase.execute()
        }


    fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            if(privateCharactersList.value.data == null)
                getCharactersFromDb().join()
            checkInternetConnection().join()
            if(privateCharactersList.value !is State.NoInternet
                && curPage < totalPageNum) {
               // Log.d("listNetwork", "loading page $curPage")
                getCharactersFromApi().join()
                saveCharactersToDb(charactersList = privateCharactersList.value.data)
            }
        }
    }


    fun saveCharactersToDb(characterModel: CharacterModel? = null,
                           charactersList: List<CharacterModel>? = null){
        viewModelScope.launch(Dispatchers.IO) {
            upsertCharactersIntoDbUsecase.execute(characterModel, charactersList)
        }
    }
    private fun checkInternetConnection() =
        viewModelScope.launch {
            privateCharactersList.value = checkInternetConnectionUsecase.execute()
        }


}