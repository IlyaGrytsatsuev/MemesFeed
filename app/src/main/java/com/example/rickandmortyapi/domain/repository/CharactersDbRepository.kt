package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus

interface CharactersDbRepository {
    suspend fun getCharactersFromDB(name:String? = null,
                                    status: CharacterStatus? = null,
                                    gender: CharacterGender? = null)
    : List<CharacterModel>
    suspend fun upsertCharatersIntoDb(character: CharacterModel? = null,
                                      characterList: List<CharacterModel>? = null)



}