package com.example.rickandmortyapi.data.network.repository

import android.util.Log
import com.example.rickandmortyapi.data.PaginationData
import com.example.rickandmortyapi.data.converters.getPagesNum
import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.data.converters.toDomainCharactersModelsList
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import com.example.rickandmortyapi.utils.Constants
import com.example.rickandmortyapi.utils.InternetConnectionChecker
import javax.inject.Inject


//todo()
//возврат списков из реплозиториев, стейты во вьюмодели , ошибки во вьюмодели , стейты в презентер, подсчет страниц в репозитория, хранение в синглтон классе,
// возрат model без обертки Response в service, кэширование в api репозитории
class CharactersApiRepositoryImpl @Inject constructor
    (
    private val charactersApiService: CharactersApiService,
    private val dbRepository: CharactersDbRepository,
    private val paginationDataRepository: PaginationDataRepository,
    private val internetConnectionChecker: InternetConnectionChecker
) : CharactersApiRepository {

    override suspend fun getCharactersList(name:String?, status: CharacterStatus?,
                                           gender: CharacterGender?): List<CharacterModel> {
        var resultList:List<CharacterModel> = listOf()
        val hasInternet = internetConnectionChecker.hasInternetConnection()
        if(hasInternet) {
            resultList = getCharactersFromApi(
                name = name,
                status = status,
                gender = gender
            )
            Log.d("netlist", "api call success")
            dbRepository.upsertCharatersIntoDb(characterList = resultList.toList())
        }
        else {
            resultList = dbRepository.getCharactersFromDB(
                name = name,
                status = status,
                gender = gender
            )
            Log.d("netlist", "db call")
        }

        paginationDataRepository.incrementPageCounter()
        paginationDataRepository.increaseDisplayedItemsNum()
        Log.d("netlist", "page = ${paginationDataRepository.getCurPage()} " +
                "items = ${paginationDataRepository.getDisplayedItemsNum()}")

        return resultList
    }

    private suspend fun getCharactersFromApi(name:String?, status: CharacterStatus?,
                                             gender: CharacterGender?): List<CharacterModel> =
        if (paginationDataRepository.hasPagesInfo())
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
        if (paginationDataRepository.getCurPage() <=
            paginationDataRepository.getTotalPageNum()) {
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
            .getCharactersList(
                page = paginationDataRepository.getCurPage(),
                name = name,
                status = status?.text,
                gender = gender?.text)
        paginationDataRepository.setTotalPageNum(apiResult.getPagesNum())
        downloadedList = apiResult.toDomainCharactersModelsList()
        return downloadedList
    }



}