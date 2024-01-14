package com.example.rickandmortyapi.domain.models

data class EpisodeModel(
    override val id:Int,
    val name:String,
    val episode:String
):RecyclerModel(id){
    companion object{
        fun newEmptyInstance(): EpisodeModel =
            EpisodeModel(id = 0, name = "",
                episode = "")

    }
}