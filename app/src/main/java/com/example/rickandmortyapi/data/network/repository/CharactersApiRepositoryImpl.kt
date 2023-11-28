package com.example.rickandmortyapi.data.network.repository

import android.util.Log
import com.example.rickandmortyapi.data.PaginationData
import com.example.rickandmortyapi.data.converters.getPagesNum
import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.data.converters.toDomainCharactersModelsList
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.utils.Constants
import javax.inject.Inject


//todo()
//возврат списков из реплозиториев, стейты во вьюмодели , ошибки во вьюмодели , стейты в презентер, подсчет страниц в репозитория, хранение в синглтон классе,
// возрат model без обертки Response в service, кэширование в api репозитории
class CharactersApiRepositoryImpl @Inject constructor
    (
    private val charactersApiService: CharactersApiService,
    private val dbRepository: CharactersDbRepository,
    private val paginationData: PaginationData
) : CharactersApiRepository {

    override suspend fun getCharactersList(): List<CharacterModel> {
        var resultList:List<CharacterModel>
        resultList = dbRepository.getCharactersFromDB()
        if(resultList.isEmpty()) {
            resultList = getCharactersFromApi()
            dbRepository.upsertCharatersIntoDb(characterList = resultList.toList())
        }
        paginationData.curPage++
        paginationData.displayedItemsNum += Constants.ITEMS_PER_PAGE
        Log.d("net", "pagination = $paginationData")
        return resultList
    }

    private suspend fun getCharactersFromApi(): List<CharacterModel> =
        if (paginationData.hasPagesInfo)
            doNextPageRequest()
        else
            downloadPage()



    private suspend fun doNextPageRequest(): List<CharacterModel> {
        var downloadedList: List<CharacterModel> = listOf()
        if (paginationData.curPage <= paginationData.totalPageNum) {
            downloadedList = downloadPage()
        }
        return downloadedList

    }

    private suspend fun downloadPage(): List<CharacterModel> {
        val downloadedList: List<CharacterModel>
        val apiResult = charactersApiService
            .getCharactersList(paginationData.curPage)
        paginationData.totalPageNum = apiResult.getPagesNum()
        downloadedList = apiResult.toDomainCharactersModelsList()
        return downloadedList
    }


}