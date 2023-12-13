package com.example.rickandmortyapi.data.network.service

import com.example.rickandmortyapi.data.db.entities.EpisodeEntity
import com.example.rickandmortyapi.data.network.responseModels.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesApiService {

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id:Int): EpisodeResponse
}