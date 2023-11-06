package com.example.memesfeed.data.network.responseModels

import com.google.gson.annotations.SerializedName

data class MemesListResponse(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("success")
    val success: Boolean?
)