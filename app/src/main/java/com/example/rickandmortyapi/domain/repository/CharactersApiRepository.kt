package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.utils.State

interface CharactersApiRepository {

    suspend fun getMemesListFromApi(page:Int): State<List<CharacterModel>>
}