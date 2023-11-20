package com.example.rickandmortyapi.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.usecases.CheckInternetConnectionUsecase
import com.example.rickandmortyapi.domain.usecases.GetCharactersFromDbUsecase
import com.example.rickandmortyapi.domain.usecases.GetCharactersListFromApiUseCase
import com.example.rickandmortyapi.domain.usecases.UpsertCharactersIntoDbUsecase
import com.example.rickandmortyapi.utils.Constants
import com.example.rickandmortyapi.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel (
    private val getCharactersListFromApiUseCase: GetCharactersListFromApiUseCase,
    private val checkInternetConnectionUsecase: CheckInternetConnectionUsecase,
    private val getCharactersFromDbUsecase: GetCharactersFromDbUsecase,
    private val upsertCharactersIntoDbUsecase: UpsertCharactersIntoDbUsecase
): ViewModel() {

    private val privateCharactersList:
            MutableStateFlow<State<List<CharacterModel>>>
    = MutableStateFlow(State.Loading())

    val charactersList:StateFlow<State<List<CharacterModel>>> = privateCharactersList

    //private val apiRequestQueue: List<()->Unit> = mutableListOf()

    var itemsInCacheNum = 0
    private var curPage: Int = 1
    private var totalPageNum: Int = 1
    private var hasPagesInfo = false

    private fun getCharactersFromApi() =
        viewModelScope.launch(Dispatchers.IO) {
            if(hasPagesInfo)
                doNextPageRequest()
            else
                doFirstApiRequest()
            if(privateCharactersList.value is State.NetworkSuccess)
                itemsInCacheNum+=Constants.ITEMS_PER_PAGE
        }

    private suspend fun doFirstApiRequest(){
        curPage = if(itemsInCacheNum > 0)
            itemsInCacheNum/Constants.ITEMS_PER_PAGE + 1 else 1

        privateCharactersList.value = getCharactersListFromApiUseCase.execute(curPage)
        totalPageNum = privateCharactersList.value.info?.pages ?: 0
    }
    private suspend fun doNextPageRequest(){
        if(curPage <= totalPageNum) {
            privateCharactersList.value = getCharactersListFromApiUseCase.execute(curPage)
            totalPageNum = privateCharactersList.value.info?.pages ?: 0
        }
    }

   private fun getCharactersFromDb() =
        viewModelScope.launch(Dispatchers.IO){
            privateCharactersList.value = getCharactersFromDbUsecase.execute()
            itemsInCacheNum = privateCharactersList.value.data?.size ?: 0
        }


    fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            if(privateCharactersList.value.data == null)
                getCharactersFromDb().join()
            checkInternetConnection().join()
            if(privateCharactersList.value !is State.NoInternet) {
                // Log.d("listNetwork", "loading page $curPage")
                getCharactersFromApi().join()
                saveCharactersToDb(charactersList = privateCharactersList.value.data)
                curPage = itemsInCacheNum/Constants.ITEMS_PER_PAGE + 1
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