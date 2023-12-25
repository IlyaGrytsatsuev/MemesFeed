package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.State

interface EpisodesApiRepository {
    suspend fun getEpisodeModelById(id:Int): EpisodeModel

    suspend fun getEpisodeDetailsModelById(id:Int): EpisodeDetailsModel

    suspend fun getEpisodesList(): List<EpisodeModel>

    suspend fun getEpisodeDetails(id: Int):  State<EpisodeDetailsModel?>
}