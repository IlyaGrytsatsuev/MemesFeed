package com.example.rickandmortyapi.domain.models

 class EpisodeDetailsModel (
     override val id:Int,
     val name: String,
     val episode: String,
     val charactersIds: List<Int>,
     var characters:List<CharacterModel>,
     override var listSize:Int = characters.size
 ): RecyclerModel()