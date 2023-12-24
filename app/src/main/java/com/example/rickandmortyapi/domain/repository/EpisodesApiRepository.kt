package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.network.responseModels.EpisodeResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodesResponse
import com.example.rickandmortyapi.domain.models.EpisodeModel

interface EpisodesApiRepository {
    suspend fun getEpisodeById(id:Int): EpisodeModel

    suspend fun getEpisodesList(): List<EpisodeModel>
}