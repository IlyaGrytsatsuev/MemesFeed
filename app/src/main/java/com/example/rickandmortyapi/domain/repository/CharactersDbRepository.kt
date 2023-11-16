package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.data.db.entities.CharactersAndEpisodesUrlsEntity
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.utils.State

interface CharactersDbRepository {
    suspend fun getCharactersFromDB(): State<List<CharacterModel>>
    suspend fun upsertCharatersIntoDb(character: CharacterModel? = null,
                                      characterList: List<CharacterModel>? = null)



}