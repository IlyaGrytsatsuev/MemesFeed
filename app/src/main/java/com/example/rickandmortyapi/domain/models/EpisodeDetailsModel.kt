package com.example.rickandmortyapi.domain.models

 class EpisodeDetailsModel (
     override val id:Int,
     val name: String,
     val episode: String,
     val charactersIds: List<Int>,
     var characters:List<CharacterModel>,
     override val listSize:Int
 ): DetailsModel(id, listSize){
     companion object{
         fun newEmptyInstance(): EpisodeDetailsModel =
             EpisodeDetailsModel(id = 0, name = "", episode = "",
                 charactersIds = emptyList(), characters = emptyList(),
                 listSize = 0
             )
     }
 }