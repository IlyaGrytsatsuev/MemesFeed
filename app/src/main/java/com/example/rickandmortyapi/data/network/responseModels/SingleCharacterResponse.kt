package com.example.rickandmortyapi.data.network.responseModels

import com.google.gson.annotations.SerializedName

data class SingleCharacterResponse(
    @SerializedName("created")
    val created: String?,
    @SerializedName("episode")
    val episode: List<String>?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("location")
    val responseLocation: ResponseLocation?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin")
    val responseOrigin: ResponseOrigin?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)
