package com.example.rickandmortyapi.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodesUrls
import com.example.rickandmortyapi.data.db.entities.CharactersAndEpisodesUrlsEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeUrlEntity

@Dao
interface CharacterDao {

    @Transaction
    @Query("select * from CharacterEntity where (:name is null or name = :name) " +
            "and (:status is null or status = :status)" +
            "and (:gender is null or gender = :gender)")
    suspend fun getCharactersWithEpisodesUrls(name:String?, status:String?,
    gender:String?): List<CharacterWithEpisodesUrls>

    @Upsert
    suspend fun upsertCharacterEntity(character: CharacterEntity)

    @Upsert
    suspend fun upsertEpisodeUrlEntity(episodeUrlEntity: EpisodeUrlEntity)

    @Upsert
    suspend fun upsertCharactersAndEpisodesUrlsEntity
                (charactersAndEpisodesUrlsEntity: CharactersAndEpisodesUrlsEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCharacterWithEpisodesUrls
                (characterWithEpisodesUrls:CharacterWithEpisodesUrls){
        val characterId =characterWithEpisodesUrls.characterEntity.id
        upsertCharacterEntity(characterWithEpisodesUrls.characterEntity)
        characterWithEpisodesUrls.episodesUrls.forEach {
            var episodeUrl = it.url
            upsertEpisodeUrlEntity(it)
            upsertCharactersAndEpisodesUrlsEntity(
                CharactersAndEpisodesUrlsEntity(characterId, episodeUrl))
        }

    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertListOfCharactersWithUrls(characterList: List<CharacterWithEpisodesUrls>){
        characterList.forEach { upsertCharacterWithEpisodesUrls(it) }
    }



}