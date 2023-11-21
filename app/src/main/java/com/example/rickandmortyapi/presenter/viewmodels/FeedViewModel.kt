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
    = MutableStateFlow(State.Loading())

    val charactersList:StateFlow<State<List<CharacterModel>>> = privateCharactersList

    private val charactersListToSaveInCache: MutableList<CharacterModel> = mutableListOf()

    var downloadedItemsNum = 0
    private var curPage: Int = 1
    private var totalPageNum: Int = 1
    private var hasPagesInfo = false
    private var dbIsEmptyFromStart = false

    private fun getCharactersFromApi() =
        viewModelScope.launch(Dispatchers.IO) {
            privateCharactersList.value = State.Loading()
            if(hasPagesInfo)
                doNextPageRequest()
            else
                doFirstApiRequest()
            Log.d("listNet", "call page${curPage}")
            if(privateCharactersList.value is State.NetworkSuccess) {
                curPage++
                downloadedItemsNum += Constants.ITEMS_PER_PAGE
                saveCharactersToDb(charactersList = privateCharactersList.value.data?.toList())

//                privateCharactersList.value.data?.let {
//                    charactersListToSaveInCache.addAll(it.toList()) }
            }
        }

    private suspend fun doFirstApiRequest(){
        curPage = if(downloadedItemsNum > 0)
            downloadedItemsNum/Constants.ITEMS_PER_PAGE + 1 else 1

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
            downloadedItemsNum = privateCharactersList.value.data?.size ?: 0
            if(downloadedItemsNum == 0)
                dbIsEmptyFromStart = true
        }


    fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            if(privateCharactersList.value.data == null
                && !dbIsEmptyFromStart)
                getCharactersFromDb().join()
            checkInternetConnection().join()
            if(privateCharactersList.value !is State.NoInternet) {
                getCharactersFromApi().join()
                //saveCharactersToDb(charactersList = privateCharactersList.value.data)
                //curPage = downloadedItemsNum/Constants.ITEMS_PER_PAGE + 1
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