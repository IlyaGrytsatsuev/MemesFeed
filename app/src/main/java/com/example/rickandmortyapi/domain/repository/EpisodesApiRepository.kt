package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.EpisodeModel

interface EpisodesApiRepository {
    suspend fun getEpisodeModelById(id:Int): EpisodeModel

    suspend fun getEpisodesList(): List<EpisodeModel>

}