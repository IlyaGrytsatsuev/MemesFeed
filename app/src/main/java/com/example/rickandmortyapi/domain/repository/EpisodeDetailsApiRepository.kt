package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.presenter.State

interface EpisodeDetailsApiRepository {
    suspend fun getEpisodeDetailsModelById(id:Int): EpisodeDetailsModel

    suspend fun getEpisodeDetails(id: Int): EpisodeDetailsModel


}