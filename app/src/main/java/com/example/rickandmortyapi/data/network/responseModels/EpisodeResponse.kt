package com.example.rickandmortyapi.data.network.responseModels

import com.google.gson.annotations.SerializedName

data class EpisodeResponse (
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name:String, //todo ?
    @SerializedName("episode")
    val episode:String,
    @SerializedName("characters")
    val characters:List<String>
)