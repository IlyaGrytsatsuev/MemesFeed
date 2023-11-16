package com.example.rickandmortyapi.data.network.service

import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharactersApiService {

    @GET("character")
    suspend fun getCharactersList(): Response<CharactersResponse>

}