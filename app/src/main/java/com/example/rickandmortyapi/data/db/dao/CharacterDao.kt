package com.example.rickandmortyapi.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.rickandmortyapi.data.db.entities.CharacterEntity
import com.example.rickandmortyapi.data.db.entities.CharacterWithEpisodes
import com.example.rickandmortyapi.data.db.entities.CharactersAndEpisodesIdsEntity
import com.example.rickandmortyapi.data.db.entities.EpisodeEntity

@Dao
interface CharacterDao {

//    @Transaction
//    @Query("select * from CharacterEntity where " +
//            "(:name = null or name LIKE :name)" +
//            "and(:status = null or status LIKE :status)" +
//            "and (:gender = null or gender LIKE :gender) " +
//            "LIMIT :limit OFFSET :offset")
//    suspend fun getCharactersWithEpisodes(limit:Int, offset:Int,
//                                          name:String?, status:String?,
//                                          gender:String?): List<CharacterWithEpisodes>

    @Transaction
    @Query("select * from CharacterEntity where characterId = :id")
    suspend fun getCharactersWithEpisodesById(id:Int): CharacterWithEpisodes?

    @Query("select * from CharacterEntity where " +
            "(:name is null or name LIKE :name)" +
            "and(:status is null or status LIKE :status)" +
            "and (:gender is null or gender LIKE :gender) " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getCharacters(limit:Int, offset:Int,
                              name:String?, status:String?,
                              gender:String?): List<CharacterEntity>

    @Upsert
    @Transaction
    suspend fun upsertCharacterEntity(characters: List<CharacterEntity>)

    @Upsert
    suspend fun upsertEpisodeEntity(episodeEntity: EpisodeEntity)

    @Upsert
    suspend fun upsertCharactersAndEpisodesIdsEntity
                (charactersAndEpisodesIdsEntity: CharactersAndEpisodesIdsEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertListOfCharacterWithEpisodes(characterWithEpisodes: CharacterWithEpisodes){
            val characterId = characterWithEpisodes.characterEntity.id
            upsertCharacterEntity(listOf(characterWithEpisodes.characterEntity))
            characterWithEpisodes.episodes.forEach {
                val episodeUrl = it.id
                upsertEpisodeEntity(it)
                upsertCharactersAndEpisodesIdsEntity(
                    CharactersAndEpisodesIdsEntity(characterId, episodeUrl))
            }
    }




}