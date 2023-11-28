package com.example.rickandmortyapi.domain.models

data class InfoModel(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)