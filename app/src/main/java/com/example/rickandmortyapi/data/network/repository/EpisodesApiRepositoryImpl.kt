package com.example.rickandmortyapi.data.network.repository

import android.util.Log
import com.example.rickandmortyapi.data.network.converters.getPagesNum
import com.example.rickandmortyapi.data.network.converters.toEpisodeDomainModel
import com.example.rickandmortyapi.data.network.converters.toEpisodeModelList
import com.example.rickandmortyapi.data.network.service.EpisodesApiService
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import com.example.rickandmortyapi.domain.repository.EpisodesDbRepository
import com.example.rickandmortyapi.domain.repository.PaginationDataRepository
import javax.inject.Inject

class EpisodesApiRepositoryImpl @Inject
constructor(private val episodesApiService: EpisodesApiService,
            private val paginationDataRepository: PaginationDataRepository,
            private val episodesDbRepository: EpisodesDbRepository)
    : EpisodesApiRepository{
    override suspend fun getEpisodeById(id: Int): EpisodeModel {
        val response = episodesApiService.getEpisodeById(id)
        return response.toEpisodeDomainModel()

    }

    override suspend fun getEpisodesList(): List<EpisodeModel> {
        var resultList:List<EpisodeModel> = listOf()
        try {
            resultList = getEpisodesApiData()
        }
        catch (e:Exception){
            resultList = getEpisodesDbData()
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

    private suspend fun getEpisodesApiData(): List<EpisodeModel>{
        if(!paginationDataRepository.isFirstLoadedFromApi()
            && paginationDataRepository.getCurPage() > 1)
            throw Exception()
        val resultList = getEpisodesFromApi()
        paginationDataRepository
            .setIsFirstLoadedFromApi(true)
        episodesDbRepository.upsertEpisodesList(resultList)
        return resultList
    }

    private suspend fun getEpisodesDbData(): List<EpisodeModel>{
        if(paginationDataRepository.getCurPage() == 1) {
            paginationDataRepository
                .setIsFirstLoadedFromApi(false)
            Log.d("netlist", "change first flag")
        }
        if(!paginationDataRepository.isFirstLoadedFromApi()) {
            val resultList = episodesDbRepository.getEpisodesList()
            Log.d("netlist", "db call")
            return resultList
        }
        throw Exception()
    }

    private suspend fun getEpisodesFromApi(): List<EpisodeModel>  =
        if (paginationDataRepository.hasPagesInfo())
            doNextPageRequest()
        else
            downloadPage()

    private suspend fun doNextPageRequest(): List<EpisodeModel>{
        var downloadedPage: List<EpisodeModel> = listOf()
        if (paginationDataRepository.getCurPage() <=
            paginationDataRepository.getTotalPageNum()) {
            downloadedPage = downloadPage()
        }
        return downloadedPage
    }

    private suspend fun downloadPage(): List<EpisodeModel>{
        val downloadedPage: List<EpisodeModel>
        val apiResult = episodesApiService
            .getEpisodes(paginationDataRepository.getCurPage())
        paginationDataRepository.setTotalPageNum(apiResult.getPagesNum())
        downloadedPage = apiResult.toEpisodeModelList()
        return downloadedPage
    }


}