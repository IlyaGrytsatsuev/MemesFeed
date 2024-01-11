package com.example.rickandmortyapi.data.network.responseModels

import com.google.gson.annotations.SerializedName

data class EpisodesResult(
    @SerializedName("air_date")
    val air_date:String?,
    @SerializedName("characters")
    val characters: List<String>?,
    @SerializedName("episode")
    val episode: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
)