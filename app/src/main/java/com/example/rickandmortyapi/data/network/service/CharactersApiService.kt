package com.example.rickandmortyapi.data.network.service

import com.example.rickandmortyapi.data.network.responseModels.CharactersResponse
import com.example.rickandmortyapi.data.network.responseModels.SingleCharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//todo nullable
interface CharactersApiService {

    @GET("character/")
    suspend fun getCharactersList(@Query("page")page: Int,
                                  @Query("name")name:String?,
                                  @Query("status")status:String?,
                                  @Query("gender")gender:String?)
    : CharactersResponse?

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id:Int): SingleCharacterResponse?



}