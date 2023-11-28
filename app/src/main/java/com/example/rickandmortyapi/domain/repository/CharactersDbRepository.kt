package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.CharacterModel

interface CharactersDbRepository {
    suspend fun getCharactersFromDB(
//        page:Int
//        name:String? = null,
//        status: CharacterStatus? = null,
//        gender: CharacterGender? = null
    )
    : List<CharacterModel>
    suspend fun upsertCharatersIntoDb(character: CharacterModel? = null,
                                      characterList: List<CharacterModel>? = null)



}