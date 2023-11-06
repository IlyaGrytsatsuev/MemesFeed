package com.example.memesfeed.data.network.service

import com.example.memesfeed.data.network.responseModels.MemesListResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface MemesApiService {

    @GET("get_memes")
    suspend fun getMemesList(): Response<MemesListResponse>

}