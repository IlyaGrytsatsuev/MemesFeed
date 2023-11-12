package com.example.rickandmortyapi.data.network.service

import retrofit2.Response
import retrofit2.http.GET

interface MemesApiService {

    @GET("get_memes")
    suspend fun getMemesList(): Response<MemesListResponse>

}