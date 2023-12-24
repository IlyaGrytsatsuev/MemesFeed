package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.domain.models.EpisodeModel

interface EpisodesDbRepository {

    suspend fun upsertEpisodesList(episodesList:List<EpisodeModel>)

    suspend fun getEpisodesList(): List<EpisodeModel>

}