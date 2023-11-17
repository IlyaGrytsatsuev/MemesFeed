package com.example.rickandmortyapi.data.network.service

import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApiService {

    @GET("character/")
    suspend fun getCharactersList(@Query("page")page: Int): Response<CharactersResponse>

}