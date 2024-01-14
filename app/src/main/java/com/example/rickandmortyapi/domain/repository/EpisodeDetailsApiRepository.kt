package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel

interface EpisodeDetailsApiRepository {
    suspend fun getEpisodeDetailsModelById(id:Int): EpisodeDetailsModel

    suspend fun getEpisodeDetails(id: Int): EpisodeDetailsModel


}