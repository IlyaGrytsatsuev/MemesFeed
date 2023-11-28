package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.CharacterModel

interface CharactersApiRepository {

    suspend fun getCharactersList()
    : List<CharacterModel>



}