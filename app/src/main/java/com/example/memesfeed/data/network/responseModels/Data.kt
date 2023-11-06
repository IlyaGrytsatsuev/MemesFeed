package com.example.memesfeed.data.network.responseModels

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("memes")
    val memes: List<Meme>?
)