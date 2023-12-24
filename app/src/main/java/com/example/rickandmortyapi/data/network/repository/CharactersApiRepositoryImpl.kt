package com.example.rickandmortyapi.data.network.repository

import android.util.Log
import com.example.rickandmortyapi.data.network.converters.appendEpisodesDetails
import com.example.rickandmortyapi.data.network.converters.getPagesNum
import com.example.rickandmortyapi.data.network.converters.toCharacterDetailsDomainModel
import com.example.rickandmortyapi.data.network.converters.toDomainCharactersModelsList
import com.example.rickandmortyapi.data.network.service.CharactersApiService
import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.domain.repository.CharactersApiRepository
import com.example.rickandmortyapi.domain.repository.CharactersDbRepository
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import javax.inject.Inject


class CharactersApiRepositoryImpl @Inject constructor
    (private val charactersApiService: CharactersApiService,
     private val charactersDbRepository: CharactersDbRepository,
     private val episodesApiRepository: EpisodesApiRepository,
     private val paginationDataRepository: PaginationDataRepository)
    : CharactersApiRepository {

    override suspend fun getCharactersList(name:String?, status: CharacterStatus?,
                                           gender: CharacterGender?): List<CharacterModel> {
        var resultList:List<CharacterModel> = listOf()
        try {
            resultList = getCharactersApiData(name = name, status = status,
                gender = gender)
        }
        catch (e:Exception){
            resultList = getDbCharactersData(name = name, status = status,
                gender = gender)
        }
        finally {
            if(resultList.isNotEmpty()) {
                paginationDataRepository.incrementPageCounter()
                paginationDataRepository.increaseDisplayedItemsNum()
            }
            Log.d("netlist", "page = ${paginationDataRepository.getCurPage()} " +
                    "items = ${paginationDataRepository.getDisplayedItemsNum()}")

        }

        return resultList
    }

    private suspend fun getCharactersApiData(name:String?, status: CharacterStatus?,
                                             gender: CharacterGender?) : List<CharacterModel>{
        if(!paginationDataRepository.isFirstLoadedFromApi()
            && paginationDataRepository.getCurPage() > 1)
            throw Exception()
        val resultList = getCharactersFromApi(
            name = name,
            status = status,
            gender = gender
        )
        Log.d("netlist", "api call success")
        paginationDataRepository
            .setIsFirstLoadedFromApi(true)
        charactersDbRepository.upsertCharactersIntoDb(characterList = resultList.toList())
        return resultList
    }
    private suspend fun getDbCharactersData(name:String?, status: CharacterStatus?,
                                            gender: CharacterGender?) : List<CharacterModel>{
        if(paginationDataRepository.getCurPage() == 1) {
            paginationDataRepository
                .setIsFirstLoadedFromApi(false)
            Log.d("netlist", "change first flag")
        }

        if(!paginationDataRepository.isFirstLoadedFromApi()) {
            val resultList = charactersDbRepository.getCharactersFromDB(
                id = null,
                name = name,
                status = status,
                gender = gender
            )
            Log.d("netlist", "db call")

            return resultList
        }
        throw Exception()
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

    private suspend fun getCharacterFromApiById(id:Int): CharacterDetailsModel {
        val response = charactersApiService.getCharacterById(id)
        val result = response.toCharacterDetailsDomainModel()

        return result
    }
    override suspend fun getCharacterDetails(id:Int): State<RecyclerModel?> {

        var characterDetails: CharacterDetailsModel? = null
        val episodesList = mutableListOf<EpisodeModel>()
        try{
            characterDetails = getCharacterFromApiById(id)

            characterDetails.episodeIds.forEach {
                episodesList.add(episodesApiRepository.getEpisodeById(it))
            }
            characterDetails.appendEpisodesDetails(episodesList)
            charactersDbRepository
                .upsertCharacterWithEpisodesIntoDb(characterDetails)
        }
        catch (e:Exception){
            characterDetails = charactersDbRepository
                .getCharacterWithEpisodesByIdFromDB(id)
            return State.Error(characterDetails)
        }
        finally {
            Log.d("netlist","Loaded Character Details " +
                    "= ${characterDetails?.episode}")
        }

        return State.Success(characterDetails)
    }




}