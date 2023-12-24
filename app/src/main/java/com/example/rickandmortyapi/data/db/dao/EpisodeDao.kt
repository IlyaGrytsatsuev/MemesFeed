package com.example.rickandmortyapi.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeWithCharacters

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM EpisodeEntity WHERE episodeId = :id")
    suspend fun getEpisodeWithCharacters(id:Int) : EpisodeEntity

    @Upsert
    suspend fun upsertEpisodeEntities(entitiesList:List<EpisodeEntity>)

    @Query("SELECT * FROM EpisodeEntity LIMIT :limit OFFSET :offset")
    suspend fun getEpisodesEntitiesList(limit:Int, offset:Int):List<EpisodeEntity>


}