package com.example.rickandmortyapi.domain.repository

import androidx.room.Query
import androidx.room.Transaction
import com.example.rickandmortyapi.data.db.entities.EpisodeWithCharacters
import com.example.rickandmortyapi.domain.models.EpisodeDetailsModel
import com.example.rickandmortyapi.domain.models.EpisodeModel

interface EpisodesDbRepository {

    suspend fun upsertEpisodesList(episodesList:List<EpisodeModel>)

    suspend fun getEpisodesList(): List<EpisodeModel>

    suspend fun upsertEpisodeWithCharactersIntoDb
                (episodeWithCharacters: EpisodeWithCharacters)

    suspend fun getEpisodeWithCharactersFromDB(id:Int):EpisodeDetailsModel?
}