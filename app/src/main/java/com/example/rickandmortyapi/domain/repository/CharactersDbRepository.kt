package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus

interface CharactersDbRepository {
    suspend fun getCharactersFromDB(id:Int?, name:String?,
                                    status: CharacterStatus?,
                                    gender: CharacterGender?)
    : List<CharacterModel>
    suspend fun upsertCharactersIntoDb(characterList: List<CharacterModel>?)

    suspend fun getCharacterWithEpisodesByIdFromDB(id:Int):CharacterDetailsModel

    suspend fun upsertCharacterWithEpisodesIntoDb(characterDetailsModel: CharacterDetailsModel)



}