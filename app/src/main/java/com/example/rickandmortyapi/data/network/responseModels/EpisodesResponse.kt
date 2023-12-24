package com.example.rickandmortyapi.data.network.responseModels

import com.google.gson.annotations.SerializedName

data class EpisodesResponse(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<EpisodesResult>?
)