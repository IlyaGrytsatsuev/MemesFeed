package com.example.rickandmortyapi.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharactersAndEpisodesIdsEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeWithCharacters

@Dao
interface EpisodeDao {

    @Transaction
    @Query("SELECT * FROM EpisodeEntity WHERE episodeId = :id")
    suspend fun getEpisodeWithCharacters(id:Int) : EpisodeWithCharacters?

    @Upsert
    suspend fun upsertEpisodeEntities(entitiesList:List<EpisodeEntity>)

    @Query("SELECT * FROM EpisodeEntity LIMIT :limit OFFSET :offset")
    suspend fun getEpisodesEntitiesList(limit:Int, offset:Int):List<EpisodeEntity>

    @Upsert
    suspend fun upsertCharacterEntity(character: CharacterEntity)

    @Upsert
    suspend fun upsertCharactersAndEpisodesIdsEntity
                (charactersAndEpisodesIdsEntity: CharactersAndEpisodesIdsEntity)

    @Transaction
    @Upsert
    suspend fun upsertEpisodeWithCharacters(episodeWithCharacters: EpisodeWithCharacters){
        val episodeId = episodeWithCharacters.episodeEntity.id
        upsertEpisodeEntities(listOf(episodeWithCharacters.episodeEntity))
        episodeWithCharacters.characters.forEach {
            val characterId = it.id
            upsertCharacterEntity(it)
            upsertCharactersAndEpisodesIdsEntity(
                CharactersAndEpisodesIdsEntity(characterId, episodeId)
            )
        }
    }



}