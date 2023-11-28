package com.example.rickandmortyapi.domain.repository

import com.example.rickandmortyapi.domain.models.CharacterModel
import com.example.rickandmortyapi.utils.CharacterGender
import com.example.rickandmortyapi.utils.CharacterStatus
import com.example.rickandmortyapi.utils.State
import retrofit2.http.Query

interface CharactersApiRepository {

    suspend fun getMemesListFromApi(page:Int, name:String? = null,
                                    status:CharacterStatus? = null,
                                    gender:CharacterGender? = null)
    : State<List<CharacterModel>>

}