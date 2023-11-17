package com.example.rickandmortyapi.domain.models

import com.google.gson.annotations.SerializedName

data class InfoModel(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)