package com.example.rickandmortyapi.data.network.responseModels

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("pages")
    val pages: Int?,
)