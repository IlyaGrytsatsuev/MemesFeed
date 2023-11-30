package com.example.rickandmortyapi.data.network.repository

import android.util.Log
import com.example.rickandmortyapi.data.PaginationData
import com.example.rickandmortyapi.data.converters.getPagesNum
import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.data.converters.toDomainCharactersModelsList
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
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

    override suspend fun getCharactersList(name:String?, status: CharacterStatus?,
                                           gender: CharacterGender?): List<CharacterModel> {
        var resultList:List<CharacterModel> = listOf()
        if(paginationData.isWholePageLoaded)
            resultList = dbRepository.getCharactersFromDB(
                name = name,
                status = status,
                gender = gender
            )
        if(resultList.isEmpty()) {
            resultList = getCharactersFromApi(name = name,
                status = status,
                gender = gender)
            Log.d("net", "api call")
            dbRepository.upsertCharatersIntoDb(characterList = resultList.toList())
        }
        val curDisplayedItemsNum = paginationData.displayedItemsNum + resultList.size
        if(curDisplayedItemsNum % Constants.ITEMS_PER_PAGE == 0){
            paginationData.curPage++
            paginationData.displayedItemsNum += Constants.ITEMS_PER_PAGE
            paginationData.isWholePageLoaded = true
        }
        else
           paginationData.isWholePageLoaded = false

        Log.d("net", "pagination = $paginationData")
        return resultList
    }

    private suspend fun getCharactersFromApi(name:String?, status: CharacterStatus?,
                                             gender: CharacterGender?): List<CharacterModel> =
        if (paginationData.hasPagesInfo)
            doNextPageRequest(name = name,
                status = status,
                gender = gender)
        else
            downloadPage(name = name,
                status = status,
                gender = gender)



    private suspend fun doNextPageRequest(name:String?, status: CharacterStatus?,
                                          gender: CharacterGender?): List<CharacterModel> {
        var downloadedList: List<CharacterModel> = listOf()
        if (paginationData.curPage <= paginationData.totalPageNum) {
            downloadedList = downloadPage(name = name,
                status = status,
                gender = gender)
        }
        return downloadedList

    }

    private suspend fun downloadPage(name:String?, status: CharacterStatus?,
                                     gender: CharacterGender?): List<CharacterModel> {
        val downloadedList: List<CharacterModel>
        val apiResult = charactersApiService
            .getCharactersList(page = paginationData.curPage,
                name = name,
                status = status?.text,
                gender = gender?.text)
        paginationData.totalPageNum = apiResult.getPagesNum()
        downloadedList = apiResult.toDomainCharactersModelsList()
        return downloadedList
    }

    override fun clearPaginationData(){
        paginationData.curPage = 1
        paginationData.displayedItemsNum = 0
        paginationData.totalPageNum = 1
        paginationData.hasPagesInfo = false
        paginationData.isWholePageLoaded = true
    }


}