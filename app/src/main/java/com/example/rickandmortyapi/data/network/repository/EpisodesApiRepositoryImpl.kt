package com.example.rickandmortyapi.data.network.repository

import android.util.Log
import com.example.rickandmortyapi.data.converters.toEpisodeDomainModel
import com.example.rickandmortyapi.data.network.responseModels.EpisodeResponse
import com.example.rickandmortyapi.data.network.service.EpisodesApiService
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.repository.EpisodesApiRepository
import javax.inject.Inject

class EpisodesApiRepositoryImpl @Inject
constructor(private val episodesApiService: EpisodesApiService) : EpisodesApiRepository{
    override suspend fun getEpisodeById(id: Int): EpisodeModel {
        val response = episodesApiService.getEpisodeById(id)
        //Log.d("netlist", "episodeRespponse = $response")
        return response.toEpisodeDomainModel()

    }

}