package com.example.rickandmortyapi.data.network.service

import com.example.rickandmortyapi.data.network.responseModels.SingleEpisodeResponse
import com.example.rickandmortyapi.data.network.responseModels.EpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesApiService {

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id:Int): SingleEpisodeResponse

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page:Int): EpisodesResponse
}