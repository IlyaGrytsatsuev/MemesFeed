package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.State

interface EpisodesApiRepository {
    suspend fun getEpisodeModelById(id:Int): EpisodeModel

    suspend fun getEpisodesList(): List<EpisodeModel>

}