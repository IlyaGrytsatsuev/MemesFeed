package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.domain.models.EpisodeModel
import com.example.rickandmortyapi.domain.models.RecyclerModel
import com.example.rickandmortyapi.presenter.State
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus

interface CharactersApiRepository {

    suspend fun getCharactersList(name:String?, status: CharacterStatus?,
                                  gender: CharacterGender?)
    : List<CharacterModel>

    suspend fun getCharacterDetails(id:Int): State<RecyclerModel?>

    suspend fun getCharacterModelById(id:Int): CharacterModel



}