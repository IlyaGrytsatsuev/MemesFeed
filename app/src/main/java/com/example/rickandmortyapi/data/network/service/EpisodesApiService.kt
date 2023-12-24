package com.example.rickandmortyapi.data.network.service

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.network.responseModels.EpisodeResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesApiService {

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id:Int): EpisodeResponse

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page:Int): EpisodesResponse
}