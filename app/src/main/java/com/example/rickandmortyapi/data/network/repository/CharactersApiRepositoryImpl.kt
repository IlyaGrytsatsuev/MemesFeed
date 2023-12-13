package com.example.rickandmortyapi.data.network.repository

import android.util.Log
import com.example.rickandmortyapi.data.converters.appendEpisodesDetails
import com.example.rickandmortyapi.data.converters.getPagesNum
import com.example.rickandmortyapi.data.converters.toCharacterDetailsDomainModel
import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.data.converters.toDomainCharactersModelsList
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import com.example.rickandmortyapi.domain.repository.EpisodesDbRepository
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import com.example.rickandmortyapi.utils.InternetConnectionChecker
import javax.inject.Inject


class CharactersApiRepositoryImpl @Inject constructor
    (
    private val charactersApiService: CharactersApiService,
    private val charactersDbRepository: CharactersDbRepository,
    private val episodesApiRepository: EpisodesApiRepository,
    private val episodesDbRepository: EpisodesDbRepository,
    private val paginationDataRepository: PaginationDataRepository,
    private val internetConnectionChecker: InternetConnectionChecker
) : CharactersApiRepository {

    override suspend fun getCharactersList(name:String?, status: CharacterStatus?,
                                           gender: CharacterGender?): List<CharacterModel> {
        val resultList:List<CharacterModel>
        val hasInternet = internetConnectionChecker.hasInternetConnection()
        if(hasInternet) {
            resultList = getCharactersFromApi(
                name = name,
                status = status,
                gender = gender
            )
            Log.d("netlist", "api call success")
            charactersDbRepository.upsertCharactersIntoDb(characterList = resultList.toList())
        }
        else {
            resultList = charactersDbRepository.getCharactersFromDB(
                id = null,
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
            downloadedList = downloadPage(
                name = name,
                status = status,
                gender = gender)
        }
        return downloadedList

    }

    private suspend fun downloadPage( name:String?, status: CharacterStatus?,
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

    private suspend fun getCharacterFromApiById(id:Int): CharacterDetailsModel {
        val response = charactersApiService.getCharacterById(id)
        val result = response.toCharacterDetailsDomainModel()

        return result
    }
    override suspend fun getCharacterDetails(id:Int): CharacterDetailsModel{
        val hasInternet = internetConnectionChecker.hasInternetConnection()
        var characterDetails: CharacterDetailsModel
        val episodesList = mutableListOf<EpisodeModel>()
        if(hasInternet) {
            characterDetails = getCharacterFromApiById(id)

            characterDetails.episodeIds.forEach {
                episodesList.add(episodesApiRepository.getEpisodeById(it))
            }
            characterDetails.appendEpisodesDetails(episodesList)
            charactersDbRepository
                .upsertCharacterWithEpisodesIntoDb(characterDetails)
        }
        else{
            characterDetails = charactersDbRepository
                .getCharacterWithEpisodesByIdFromDB(id)
        }

        Log.d("netlist","Loaded Character Details " +
                "= ${characterDetails.episode}")


        return characterDetails
    }




}