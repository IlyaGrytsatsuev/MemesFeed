package com.example.rickandmortyapi.domain.models

data class EpisodeModel(
    override val id:Int = 0,
    val name:String = "",
    val episode:String = ""
):RecyclerModel()