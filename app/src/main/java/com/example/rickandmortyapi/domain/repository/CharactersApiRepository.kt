package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.CharacterDetailsModel
import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus

interface CharactersApiRepository {

    suspend fun getCharactersList(name:String?, status: CharacterStatus?,
                                  gender: CharacterGender?)
    : List<CharacterModel>

    suspend fun getCharacterDetails(id:Int): CharacterDetailsModel?




}